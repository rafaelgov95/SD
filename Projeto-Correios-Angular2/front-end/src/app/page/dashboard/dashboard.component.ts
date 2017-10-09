import { buscaEncomenda } from './../../shared/models/buscaEncomendas';
import { Encomendas } from './../../shared/services/buscas/Encomendas';
import { Usuario } from './../../shared/models/usuario';
import { Component } from '@angular/core';
import { Buscas } from './../../shared/services/buscas/Buscas';
import { Subscription } from 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';
//-- Paginetor

import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [Encomendas]
})

export class DashboardComponent {
  type: string
  input: string
  resultCEP: Usuario
  textoCEP: string;
  resultRast: buscaEncomenda
  constructor(private buscas: Buscas, private servico: Encomendas) {
    this.type = "Tipo"
    // this.resultCEP = new Usuario("", "", "", "", "", "", "", "", "")
    // this.resultRast = new buscaEncomenda("", "", "", "")
    
  }

  buscar() {
    if (this.type == "Tipo") {
      alert("Por Favor Escolha Tipo")
    } else if (this.type == "CEP") {
      this.resultRast = null;
      this.buscas.getCEP(this.input).subscribe(data => {
        console.log(data)
        this.resultCEP = new Usuario(data.cep, data.logradouro, data.complemento, data.bairro, data.localidade, data.uf, data.unidade, data.ibge, data.gia)
      }, err => console.log("Erro"))
    } else if (this.type == "Rastreio") {
      this.resultCEP = null;
      this.servico.getEncomenda(this.input).subscribe(data => {
        data=data['rast']
        this.resultRast = new buscaEncomenda(data.codigo,data.data,data.local,data.situacao)
        console.log(this.resultRast)
      }, err => console.log("Erro"))

    }
  }
}
