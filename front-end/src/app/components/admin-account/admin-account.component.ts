import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';

import { Observable } from 'rxjs/Observable';


@Component({
  selector: 'app-admin-account',
  templateUrl: './admin-account.component.html',
  styleUrls: ['./admin-account.component.sass']
})
export class AdminAccountComponent implements OnInit {

  public displayedColumns = ['email', 'role', 'action'];
  @ViewChild('filter') filter: ElementRef;
  dataSource = ELEMENT_DATA;

  constructor() {}
  ngOnInit() {
  }

}

export interface Element {
  email: string;
  role: string;
}

const ELEMENT_DATA: Element[] = [
 {email: 'bboca@yahoo.comm', role: 'admin'},
 {email: 'bboca@yahoo.comm', role: 'admin'},
 {email: 'bboca@yahoo.comm', role: 'admin'}
];
