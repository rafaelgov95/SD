import { DashboardComponent } from './dashboard/dashboard.component';
import { PipesModule } from './../shared/pipes/pipes-module';
import { SafeHtmlPipe } from './../shared/pipes/htmlview';
import { RouterModule,Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BlogRoutingModule } from './blog-routing.module';
import { BlogComponent } from './blog.component';



@NgModule({
  imports: [
    CommonModule,
    BlogRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    PipesModule
   
  ],
  declarations: [BlogComponent,DashboardComponent],
  providers: [],
  exports: [PipesModule,DashboardComponent]
})
export class BlogModule { }
