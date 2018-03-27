import { Component, OnInit } from '@angular/core';
import { Project } from '../../shared/interfaces/project';
import { PageEvent } from '@angular/material';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.sass']
})
export class ProjectListComponent implements OnInit {

  project: Project;
  projects = [];

  // MatPaginator Inputs
  length = 100;
  pageSize = 6;
  pageSizeOptions = [6, 12, 24, 48];

  // MatPaginator Output
  pageEvent: PageEvent;

  constructor() {
    this.project = {
      title: 'Aplicatii ale problemelor de tip Stable Matching',
      description: 'At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis \
                    praesentium voluptatum deleniti atque corrupti quos dolores et quas molestia',
      tags: [
        {id: 1, name: 'Web'},
        {id: 2, name: 'Spring'},
        {id: 3, name: 'Java'}
      ],

      bibliographies: [
        {id: 1, name: 'www.google.com'},
        {id: 2, name: 'www.spring.io'},
        {id: 3, name: 'www.wikipedia.com'}
      ],
      lecturer : {
        name: 'Frasinaru Cristian',
        email: 'acf@info.uaic.ro',
        cabinetNumber: 'C201',
        projects : []
      }
    };
  }

  ngOnInit() {
    for (let i = 0; i < this.pageSize; i++) {
      this.projects.push(this.project);
    }
  }

  fakeServerData(event?: PageEvent): void {
    console.log(event.pageSize);
    this.project.title = 'Pagina ' + event.pageIndex;
    this.projects = [];
    for (let i = 0; i < event.pageSize; i++) {
      this.projects.push(this.project);
    }
  }
}
