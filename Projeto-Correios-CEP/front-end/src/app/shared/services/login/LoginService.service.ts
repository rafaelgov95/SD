import { Observable } from 'rxjs';
import { Login } from './../../models/login';
import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';


@Injectable()
export class LoginService {
    Url:string='http://localhost:3000/';
    // Url:string='/';
    
    headers = new Headers();
    constructor(private http: Http) {
        this.headers.append('Content-Type', 'application/json');
    }


    logar(email: string, senha: string): Observable<any> {
        let body = JSON.stringify({ email: email, senha: senha })

        return this.http.post(this.Url+'api/autentica', body, { headers: this.headers })
            .map((res: Response) => {
                if (res.status < 200 || res.status >= 300) {
                    throw new Error('Requesição Falhou' + res.status);
                }
                else {
                    let body = res.json();
                    console.log("Body", body)
                    if (body) {
                        if (body.user && body.user.accessToken) {
                            localStorage.setItem('currentUser', JSON.stringify(body.user));
                        }
                    }

                }
            });
    }

    create(user: Login) {
        return this.http.post(this.Url+'api/login/save', user).map((response: Response) => response.json());
    }


    logout() {
        sessionStorage.removeItem('currentUser');
    }
}