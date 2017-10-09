import { Cep } from './../../shared/models/Cep';
import { PatternValidator, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { buscaEncomenda } from './../../shared/models/buscaEncomendas';
import { Encomendas } from './../../shared/services/buscas/Encomendas';
import { Component,ViewContainerRef } from '@angular/core';
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
  inputCep: string
  inputRast: string
  resultCEP: Cep
  UserForm: FormGroup;
  textoCEP: string;
  resultRast: buscaEncomenda
  constructor(private buscas: Buscas, private servico: Encomendas, private fb: FormBuilder,public toastr: ToastsManager, vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr)
    this.type = "Tipo"
    // this.resultCEP = new Cep("", "", "", "", "", "", "", "", "")
    // this.resultRast = new buscaEncomenda("", "", "", "")
  }

  buscar() {
    if (this.type == "Tipo") {
      this.showInfo()
    } else if (this.type == "CEP") {
      this.resultRast = null;
      this.buscas.getCEP(this.UserForm.controls['cep'].value).subscribe(data => {
        this.resultCEP = new Cep(data.cep, data.logradouro, data.complemento, data.bairro, data.localidade, data.uf, data.unidade, data.ibge, data.gia)
        this.cshowSuccess()
      }, err => this.cshowError())
    } else if (this.type == "Rastreio") {
      this.resultCEP = null;
      this.servico.getEncomenda(this.UserForm.controls['rast'].value).subscribe(data => {
        data = data['rast']
        this.resultRast = new buscaEncomenda(data.codigo, data.data, data.local, data.situacao)
        this.rshowSuccess()
      }, err =>  this.rshowError())

    }
  }


  ngOnInit(): void {
    this.buildForm();
  }

  buildForm(): void {
    this.UserForm = this.fb.group({
      'cep': [this.inputCep, [
        Validators.required,
        Validators.minLength(8),

        Validators.maxLength(8)]
      ],
      'rast': [this.inputRast, [
        Validators.required,
        Validators.minLength(5)
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
    'cep': '',
    'rast': ''
  };

  validationMessages = {
    'cep': {
      'required': 'CEP Requerido.',
      'minlength': 'CEP possui mais caracteres',
      'maxlength': 'Cep possui menos caracteres'
    }
    ,
    'rast': {
      'required': 'Código de Rastreamento Requerido',
      'minlength': 'Código Pequeno',
      'maxlength': 'Código Muito grande'
    }
  };

  rshowSuccess() {
    this.toastr.success('Objeto rastreado com sucesso!!!');
  }

  rshowError() {
    this.toastr.error('Erro ao rastrear objeto!!!');
  }

  cshowSuccess() {
    this.toastr.success('Cep encontrado com Sucesso!!!');
  }

  cshowError() {
    this.toastr.error('Erro ao procurar CEP!!!');
  }
  showInfo() {
    this.toastr.info('Selecione um Tipo de busca !');
  }
}

