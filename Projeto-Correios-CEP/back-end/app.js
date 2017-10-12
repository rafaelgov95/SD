'use strict'
/*
    @name      API Rastreamendo de objetos e CEP - Correios - NodeJS
    @author    Rafael Viana <rafael.viana@aluno.ufms.br>
 */
var path = require('path');
var express = require('express');
var configs = require('./configs/config');
var WebSRO = require('./bin/parser');
var HTTPStatus = require('http-status-codes');
var responses = require("./bin/responses");
var bodyParser = require('body-parser')
var soap = require('soap');
var app = express();
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.use(express.static(path.join(__dirname, 'public')));

app.use(function (req, res, next) {
    res.setHeader('x-powered-by', configs.app_name + " - " + configs.app_version);
    res.setHeader('Access-Control-Allow-Credentials', true);
    res.setHeader('Access-Control-Allow-Origin', configs.app_origin);
    res.setHeader('Access-Control-Allow-Methods', '*');
    res.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    next();
});


app.get('/api/correios/json/cep', function (req, res) {
    var url = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl";
    var options = {
        ignoredNamespaces: {
            namespaces: ['targetNamespace', 'typedNamespace'],
            override: true
        }
    };
    var cep = req.query.cep.replace("/\D/", '');
    if (cep.length == 8) {
        soap.createClient(url, options, function (err, client) {
            if (err) {
                res.json(false);
            } else {
                console.log(client.describe())
                client.consultaCEP({ cep: req.query.cep }, function (errCli, result) {
                    res.json(errCli ? false : result['return']);
                });
            }
        });
    } else {
        res.json(false);
    }
});

app.get("/api/correios/json/objeto", function (req, res) {
    var url = "http://webservice.correios.com.br/service/rastro/Rastro.wsdl";
    var objeto = req.query.code;
    soap.createClient(url, function (err, client) {
        if (err) {
            res.send("ERRO")
        } else {
            client.buscaEventos({ usuario: "ECT", senha: "SRO", tipo: "L", resultado: "T", lingua: 101, objetos: objeto }, function (errCli, result) {
                res.json(errCli ? false : result['return']);
            });
        }
    });
});

app.get('/api/correios/json/altobjeto', function (req, res) {
    WebSRO.request(req)
        .then(function (data) {
            var response = WebSRO.parser(data);
            responses(req.params.type, response, HTTPStatus.OK, res);

        }).catch(function (err) {
            var messages = {};
            messages[HTTPStatus.BAD_REQUEST] = 'Código de rastreio inválido.';
            messages[HTTPStatus.REQUEST_TIMEOUT] = 'Erro ao realizar conexão com o webSRO.';
            messages[HTTPStatus.NOT_FOUND] = 'Não foram encontrados dados de rastreio para o código informado.';

            var code = err.statusCode || HTTPStatus.REQUEST_TIMEOUT;
            var error = {
                status: false,
                code: code,
                message: messages[code]
            };
            responses(req.params.type, error, error.code, res);
        });
});


app.use('/*', function (req, res) {
    res.sendFile(__dirname + '/public/index.html');
});


module.exports = app;