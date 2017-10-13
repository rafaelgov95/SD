import { Cep } from './../../models/Cep';
import { Observable } from 'rxjs';
import { Injectable, EventEmitter } from '@angular/core';
import { Http, Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';
import 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/switchMap';

@Injectable()
export class Buscas {
  emitterDelivery = new EventEmitter();
  headers: Headers;
  params: URLSearchParams;


  constructor(private http: Http) {

    this.headers = new Headers({
      'Content-Type': 'application/json'
    });
    
  }

  getCEP(cep: string): Observable<Cep> {
    let options = new RequestOptions({ headers: this.headers });
    return this.http.get('/api/correios/json/cep/'+cep,options)
      .map((response: Response) => response.json());
  }

 
}