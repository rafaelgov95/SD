/*
    @name      API Rastreamendo de objetos - Correios - NodeJS
    @author    Rafael Viana <rafael.viana@aluno.ufms.br>

    Meio de acessar o servidor do correios por meio de Web Scraping e Web Crawler.

 */

'use strict'
var path = require('path');
var express     = require('express');
var configs     = require('./configs/config');
var WebSRO      = require('./bin/parser');
var HTTPStatus  = require('http-status-codes');
var responses   = require("./bin/responses");

var app = express();

app.use(express.static(path.join(__dirname, 'public')));

/**
 * Configurações básicas de cabeçalhos HTTP
 */
app.use(function (req, res, next) {
    res.setHeader('x-powered-by',                       configs.app_name+" - "+configs.app_version);
    res.setHeader('Access-Control-Allow-Credentials',   true);
    res.setHeader('Access-Control-Allow-Origin',        configs.app_origin);
    res.setHeader('Access-Control-Allow-Methods',       'GET');
    res.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    next();
});

/**
 * Rota padrão, com informaçõa do app e versão
 */


app.get("/info", function (req, res) {
    return res.status(HTTPStatus.OK).json({
        status  : true,
        code    : HTTPStatus.OK,
        message : configs.app_name+" - "+configs.app_version
    });
});

/**
 * Rota onde não existe código de rastreio, informa a pendência
 */
app.get('/:type/', function (req, res) {
    var error = {
        status  : false,
        code    : HTTPStatus.BAD_REQUEST,
        message : 'Informe um código de rastreio (EX:SS123456789BR)'
    };
    responses(req.params.type,error,error.code,res);
});

/**
 * Rota com código de rastreio presnete nos params
 * Realiza request e parse do response HTTP
 */
app.get('/:type/:code', function (req, res) {
    WebSRO.request(req)
        .then(function (data) {

            /**
             * Caso o request tenha sido bem sucedido
             * tenta realizar parse do HTML e por fim retornar
             * os dados de rastreio.
             */
            var response = WebSRO.parser(data);
            responses(req.params.type,response,HTTPStatus.OK,res);
    
        }).catch(function (err) {

        /**
         * Caso algum erro tenha ocorrido
         * o mesmo será retornado de acordo com
         * as mensagens abaixo.
         * @type {{}}
         */
        var messages = {};
            messages[HTTPStatus.BAD_REQUEST]     ='Código de rastreio inválido.';
            messages[HTTPStatus.REQUEST_TIMEOUT] ='Erro ao realizar conexão com o webSRO.';
            messages[HTTPStatus.NOT_FOUND]       ='Não foram encontrados dados de rastreio para o código informado.';

            var code = err.statusCode || HTTPStatus.REQUEST_TIMEOUT;
            var error = {
                status  : false,
                code    : code,
                message : messages[code]
            };
            responses(req.params.type,error,error.code,res);
        });
});


app.use('/*',function (req, res) {
    res.sendFile(__dirname + '/public/index.html');
});


module.exports = app;