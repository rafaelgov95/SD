import { DashboardComponent } from './dashboard/dashboard.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.scss']
})
export class PageComponent implements OnInit {
  inscricao: Subscription;
  constructor(private router: Router) {

  }
  LOGO = {
    'background': 'url(./assets/img/back.png)  ',
    'width': '100%',
    'background-attachment': 'fixed',
    'background-position': 'center',
    'background-repeat': 'no-repeat',
    'background-size': '100% 100%'
  }

  ngOnInit() {
  }
  ngOnDestroy() {
  }

}
