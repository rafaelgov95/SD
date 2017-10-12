import { AlertService } from './../../services/alert.service';
import { LoginService } from './../../services/login/LoginService.service';
import { Login } from './../../models/login';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

import { FormGroup } from '@angular/forms';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [LoginService, AlertService]

})
export class HeaderComponent implements OnInit {
  emailRegex = '^[a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,15})$';
  alert = true;
  returnUrl: string;
  UserForm: FormGroup;
  user: Login;
  nome: String;
  submitted = false;
  logado = true;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService

  ) {
    this.user = new Login('', '', '');
    if (localStorage.getItem('currentUser')) {
      this.nome = JSON.parse(localStorage.getItem('currentUser'))['nome']
      console.log(this.nome)
      this.logado = false;
    } else {
      this.logado = true;
    }
  }

  ngOnInit(): void {

    this.buildForm();
    // this.alertService.getMessage();
    // reset login status
    this.loginService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  buildForm(): void {
    this.UserForm = this.fb.group({
      'email': [this.user.email, [
        Validators.required,
        Validators.minLength(4),
        Validators.pattern(this.emailRegex)
      ]
      ],
      'senha': [this.user.senha, [
        Validators.required,
        Validators.minLength(4)
      ]]
    });

    this.UserForm.valueChanges
      .subscribe(data => this.onValueChanged(data));

    this.onValueChanged();
  }

  onValueChanged(data?: any) {
    if (!this.UserForm) { return; }
    const form = this.UserForm;

    for (const field in this.formErrors) {
      // clear previous error message (if any)
      this.formErrors[field] = '';
      const control = form.get(field);

      if (control && control.dirty && !control.valid) {
        const messages = this.validationMessages[field];
        for (const key in control.errors) {
          this.formErrors[field] += messages[key] + ' ';
        }
      }
    }
  }

  formErrors = {
    'email': ''
  };

  validationMessages = {
    'email': {
      'required': 'Nome de usuário requerido.',
      'minlength': 'Nome tem que possuir mais de 4 caracteres',
      'pattern': 'Email esta incorreto'
    }

  };


  login() {
    this.loginService.logar(this.UserForm.get('email').value, this.UserForm.get('senha').value)
      .subscribe(
      data => {
        window.location.reload();     
      },
      error => {
        console.log('Login Erro');
        this.alert = false;
        setTimeout(() => this.alert = true, 10000);

      });
  }

  logout() {
    localStorage.clear();
    window.location.reload();
  }
}
