import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.sass']
})
export class ProjectDetailsComponent implements OnInit {

  references = [
    'www.wikipedia.com',
    'www.google.com',
    'www.spring.io'
  ];

  tags = [
    {name: 'Java'},
    {name: 'WEB'},
    {name: 'Postgre'},
    {name: 'REST'}
  ];

  constructor() { }

  ngOnInit() {
  }

}
