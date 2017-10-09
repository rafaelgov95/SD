import { Encomendas } from './../../../shared/services/buscas/Encomendas';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/map';
@Component({
  selector: 'app-encomenda',
  templateUrl: './encomenda.component.html',
  styleUrls: ['./encomenda.component.scss'],
  providers:[Encomendas] 
})
export class EncomendaComponent implements OnInit {
encomenda:string;

  constructor(private buscas: Encomendas) {

   }


   busca() {
    this.buscas.getEncomenda(this.encomenda).subscribe(data => {
      console.log(data)
     
    }, err => console.log("Erro"))

  }

  ngOnInit() {
  }

}
