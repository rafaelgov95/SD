import { Buscas } from './shared/services/buscas/Buscas';
import { MaterialModule } from '@angular/material';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
import { CustomOption } from './shared/components/toast/ng2-toast-config';
import { SafeHtmlPipe } from './shared/pipes/htmlview';
import { HeaderComponent } from './shared/components/header/header.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { Http, HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AuthGuard } from './shared';
import { AppComponent } from './app.component';
import {ToastModule} from 'ng2-toastr/ng2-toastr';


@NgModule({
  declarations: [
    AppComponent, HeaderComponent],
  imports: [
    HttpModule,
    AppRoutingModule,
    BrowserModule,
    ToastModule.forRoot(),
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
  ], exports: [
    BrowserAnimationsModule ],
  providers: [AuthGuard,Buscas],
  bootstrap: [AppComponent]
})
export class AppModule { }
