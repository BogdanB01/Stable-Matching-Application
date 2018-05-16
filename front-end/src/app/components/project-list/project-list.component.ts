import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Project } from '../../shared/interfaces/project';
import { PageEvent } from '@angular/material';
import { ProjectService } from '../../shared/services/project.service';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator } from '@angular/material';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.sass']
})
export class ProjectListComponent implements OnInit {

  projects = [];
  length = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.initEvents(0, 'all', '');

    this.paginator.page
      .pipe(
        tap(() => this.loadProjectsPage())
      )
      .subscribe();
  }

  initEvents(pageNumber: number, type: string, filter: string) {
    this.projectService.getProjects(pageNumber, this.paginator.pageSize, type, filter).subscribe((resp: any) => {
      this.projects = resp.content;
      this.length = resp.totalElements;
    });
  }

  loadProjectsPage() {
    this.projectService.getProjects(this.paginator.pageIndex, this.paginator.pageSize, '', '').subscribe(resp => {
      this.projects = resp.content;
    });
  }

}
