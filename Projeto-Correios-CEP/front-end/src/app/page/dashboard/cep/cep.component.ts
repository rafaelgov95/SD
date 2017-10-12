import { Cep } from './../../../shared/models/Cep';

import { Component } from '@angular/core';
import { Buscas } from './../../../shared/services/buscas/Buscas';
import { Subscription } from 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';
//-- Paginetor

import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-cep',
  templateUrl: './cep.component.html',
  styleUrls: ['./cep.component.scss'],
  providers:[Buscas]
})
export class CepComponent {
  cep: string
  resultCEP:Cep
  textoCEP : string;

  constructor(private buscas: Buscas) {
    this.cep=""
    this.resultCEP = new Cep("","","","","","","","","") 
   }


   buscacep() {
    this.buscas.getCEP(this.cep).subscribe(data => {
      console.log(data)
      this.resultCEP = new Cep(data.cep,data.logradouro,data.complemento,data.bairro,data.localidade,data.uf,data.unidade,data.ibge,data.gia)
    }, err => console.log("Erro"))

  }
}
