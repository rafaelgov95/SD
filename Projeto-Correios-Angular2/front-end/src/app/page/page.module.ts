import { DashboardComponent } from './dashboard/dashboard.component';
import { PipesModule } from './../shared/pipes/pipes-module';
import { SafeHtmlPipe } from './../shared/pipes/htmlview';
import { RouterModule,Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageRoutingModule } from './page-routing.module';
import { PageComponent } from './page.component';
import { EncomendaComponent } from './dashboard/encomenda/encomenda.component';
import { CepComponent } from './dashboard/cep/cep.component';



@NgModule({
  imports: [
    CommonModule,
    PageRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    PipesModule
   
  ],
  declarations: [PageComponent,DashboardComponent, EncomendaComponent, CepComponent],
  providers: [],
  exports: [PipesModule,DashboardComponent]
})
export class PageModule { }
