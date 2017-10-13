import { buscaEncomenda2 } from './../../models/buscaEncomendas2';
import { buscaEncomenda } from './../../models/buscaEncomendas';
import { Observable } from 'rxjs';
import { Injectable, EventEmitter } from '@angular/core';
import { Http, Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/switchMap';

@Injectable()
export class Encomendas {
  emitterDelivery = new EventEmitter();
  headers: Headers;
  params: URLSearchParams;


  constructor(private http: Http) {

    this.headers = new Headers({
      'Content-Type': 'application/json'
    });
    
  }

  getEncomenda2(encomenda: string): Observable<any> {
    let options = new RequestOptions({ headers: this.headers });
    return this.http.get('http://localhost:3000/api/correios/json/objeto/'+encomenda,options)
      .map((response: Response) => response.json());
  }
  getEncomenda(encomenda: string): Observable<buscaEncomenda> {
    let options = new RequestOptions({ headers: this.headers });
    return this.http.get('http://localhost:3000/api/correios/json/aobjeto/'+encomenda,options)
      .map((response: Response) => response.json());
  }

 
}