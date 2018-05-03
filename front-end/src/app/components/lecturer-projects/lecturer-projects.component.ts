import { Component, OnInit, Inject } from '@angular/core';
import { MatTableDataSource, MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTab } from '@angular/material';
import { EditProjectDialogComponent } from '../edit-project-dialog/edit-project-dialog.component';
import { StatisticsDialogComponent } from '../lecturer-project-statistics-dialog/statistics-dialog-component';
import { SearchStudentDialogComponent } from './search-student-dialog.component';
import { ProjectService } from '../../shared/services/project.service';
import { StudentService } from '../../shared/services/student.service';
import { DataSource } from '@angular/cdk/collections';
import { DeleteDialogComponent } from '../../dialogs/delete/delete.dialog.component';

@Component({
    selector: 'app-lecturer-projects',
    templateUrl: './lecturer-projects.component.html',
    styleUrls: ['./lecturer-projects.component.sass']
})
export class LecturerProjectsComponent implements OnInit {
    displayedColumns = ['title', 'capacity', 'assignedStudents', 'actions'];

    exampleDatabase: ProjectService | null;
    dataSource: ProjectDataSource | null;

    ngOnInit() {
      this.loadData();
    }

    constructor(public dialog: MatDialog,
                private studentService: StudentService,
                private projectService: ProjectService) {}

    unassignStudent(studentId, projectId): void {
      this.projectService.unassignStudent(projectId, studentId).subscribe(data => {

        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === projectId);
        this.exampleDatabase.dataChange.value[foundIndex] = data;
        this.projectService.dataChange.next(this.projectService.dataChange.value);
      }, err => {
        console.log(err);
      });
    }

    removeProject(id: number): void {

      const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === id);

      const dialogRef = this.dialog.open(DeleteDialogComponent, {
        width: '350px',
        data: {
          title: this.exampleDatabase.dataChange.value[foundIndex].title,
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
          console.log('sterge-ma');
          this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
          this.projectService.deleteProject(id);
        }
      });
    }

    loadData() {
      this.exampleDatabase = this.projectService;
      this.dataSource = new ProjectDataSource(this.exampleDatabase);
      console.log(this.dataSource);
    }

    refreshTable() {
      this.exampleDatabase.dataChange.next(0);
    }

    openSearchStudentModal(projectId: any): void {
      const dialogRef = this.dialog.open(SearchStudentDialogComponent, {
        width: '350px',
        data: {
          projectId: projectId
        }
      });

      const sub = dialogRef.componentInstance.onAssign.subscribe((data) => {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === projectId);
        this.exampleDatabase.dataChange.value[foundIndex] = data;
        this.projectService.dataChange.next(this.projectService.dataChange.value);
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }

    openEditProjectModal(project: any): void {
      console.log(project);

      const dialogRef = this.dialog.open(EditProjectDialogComponent, {
        width: '800px',
        data: project
      });

      const sub = dialogRef.componentInstance.onUpdate.subscribe((data) => {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === project.id);
        this.exampleDatabase.dataChange.value[foundIndex] = data;
        this.projectService.dataChange.next(this.projectService.dataChange.value);
      });

      dialogRef.afterClosed().subscribe(result => {
        sub.unsubscribe();
      });
    }

    openStatisticsDialog(): void {
      const dialogRef = this.dialog.open(StatisticsDialogComponent, {
        width: '600px'
      });
    }
}

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/merge';

export class ProjectDataSource extends DataSource<any> {

  renderedData = [];

  constructor(public projectService: ProjectService) {
    super();
  }

  connect(): Observable<any> {
    const changes = [
      this.projectService.dataChange
    ];

    this.projectService.getProjectsForLecturer();
    return Observable.merge(...changes).map(() => {
      this.renderedData = this.projectService.data;
      return this.renderedData;
    });
  }

  disconnect() {}
}


