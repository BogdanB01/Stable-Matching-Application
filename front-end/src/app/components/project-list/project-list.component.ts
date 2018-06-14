import { Component, OnInit, Input, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { Project } from '../../shared/interfaces/project';
import { PageEvent } from '@angular/material';
import { ProjectService } from '../../shared/services/project.service';
import { MatPaginator } from '@angular/material';
import { tap } from 'rxjs/operators';
import { ObservableMedia } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/takeWhile';
import 'rxjs/add/operator/startWith';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.sass']
})
export class ProjectListComponent implements OnInit, AfterViewInit, OnDestroy {

  projects = [];
  length = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  public cols: BehaviorSubject<number>;
  mediaSub: Subscription;

  constructor(private projectService: ProjectService,
              private observableMedia: ObservableMedia) {
                this.cols = new BehaviorSubject<number>(2);
              }

  ngOnInit() {
    this.observeResponsiveColLayout();
  }

  ngOnDestroy() {
  //  this.mediaSub.unsubscribe();
  }

  private observeResponsiveColLayout() {
    const sizeColMap = {
      'xs': 1,
      'sm': 2,
      'md': 3,
      'lg': 3,
      'xl': 4,
    };
    const activeKey = Object.keys(sizeColMap).find(key => this.observableMedia.isActive(key));
    this.cols.next(sizeColMap[activeKey]);
    this.mediaSub = this.observableMedia.asObservable()
      .subscribe(change => this.cols.next(sizeColMap[change.mqAlias]));
  }
  ngAfterViewInit(): void {
    this.initEvents(0, 'all', '');

    this.paginator.page
      .pipe(
        tap(() => this.loadProjectsPage())
      )
      .subscribe();
  }

  initEvents(pageNumber: number, type: string, filter: string) {
    console.log(this.paginator.pageSize);
    this.projectService.getProjects(pageNumber, this.paginator.pageSize, type, filter).subscribe((resp: any) => {
      console.log(resp);
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
