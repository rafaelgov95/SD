import { Usuario } from './../../models/usuario';
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

  getEncomenda(encomenda: string): Observable<Usuario> {
    let options = new RequestOptions({ headers: this.headers });
    return this.http.get('http://localhost:3000/json/'+encomenda,options)
      .map((response: Response) => response.json());
  }

 
}