import { Component, OnInit } from '@angular/core';
import { Project } from '../../shared/interfaces/project';
import { PageEvent } from '@angular/material';
import { ProjectService } from '../../shared/services/project.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.sass']
})
export class ProjectListComponent implements OnInit {

  projects = [];

  // MatPaginator Inputs
  length = 4;
  pageSize = 6;
  pageSizeOptions = [6, 12, 24, 48];

  // MatPaginator Output
  pageEvent: PageEvent;

  constructor(private projectService: ProjectService) {

  }

  ngOnInit() {
    this.initEvents(0, this.pageSize);
  }

  initEvents(pageNumber: number, pageSize: number) {
    this.projectService.getProjects(pageNumber, pageSize).subscribe((resp: any) => {
      console.log(resp);
      this.projects = resp.content;
      this.length = resp.totalElements;
    });
  }

  getServerData(event?: PageEvent): void {
    this.initEvents(event.pageIndex, event.pageSize);
  }
  // fakeServerData(event?: PageEvent): void {
  //   console.log(event.pageSize);
  //   this.project.title = 'Pagina ' + event.pageIndex;
  //   this.projects = [];
  //   for (let i = 0; i < event.pageSize; i++) {
  //     this.projects.push(this.project);
  //   }
  // }
}
