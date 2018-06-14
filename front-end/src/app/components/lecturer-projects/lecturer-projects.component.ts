import { Component, OnInit, Inject } from '@angular/core';
import { MatTableDataSource, MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTab } from '@angular/material';
import { EditProjectDialogComponent } from '../edit-project-dialog/edit-project-dialog.component';
import { StatisticsDialogComponent } from '../lecturer-project-statistics-dialog/statistics-dialog-component';
import { ProjectService } from '../../shared/services/project.service';
import { StudentService } from '../../shared/services/student.service';
import { DataSource } from '@angular/cdk/collections';
import { ConfirmDialogComponent } from '../../dialogs/confirm/confirm.dialog.component';
import { Router, ActivatedRoute } from '@angular/router';
import { SnackBarService } from '../../shared/services/snackbar.service';
import { PastProjectsComponent } from '../../dialogs/past-projects/past-projects.component';
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
                private projectService: ProjectService,
                private route: ActivatedRoute,
                private router: Router,
                private snackBarService: SnackBarService) {}

    // unassignStudent(studentId, projectId): void {
    //   this.projectService.unassignStudent(projectId, studentId).subscribe(data => {

    //     const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === projectId);
    //     this.exampleDatabase.dataChange.value[foundIndex] = data;
    //     this.projectService.dataChange.next(this.projectService.dataChange.value);
    //     this.snackBarService.showSnackBar('Studentul a fost sters cu succes!');
    //   }, err => {
    //     console.log(err);
    //     this.snackBarService.showSnackBar('A aparut o eroare la stergerea studentului!');
    //   });
    // }

    removeProject(id: number): void {

      const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === id);

      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        width: '350px',
        data: {
          title: this.exampleDatabase.dataChange.value[foundIndex].title,
          positiveButton: 'Delete'
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
        width: '1000px'
      });
    }

    goToProjectDetails(project: any): void {
      this.router.navigate([project.id, 'info'], {relativeTo: this.route});
    }

    openPastProjectsDialog(): void {
      const dialogRef = this.dialog.open(PastProjectsComponent, {
        width: '600px',
        autoFocus: false
      });

      const sub = dialogRef.componentInstance.updatedProjects.subscribe((data) => {
        this.exampleDatabase.dataChange.value.push(...data);
        this.projectService.dataChange.next(this.projectService.dataChange.value);
      });

      dialogRef.afterClosed().subscribe(result => {
        sub.unsubscribe();
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

    this.projectService.getActiveProjectsForLecturer();
    return Observable.merge(...changes).map(() => {
      this.renderedData = this.projectService.data;
      return this.renderedData;
    });
  }

  disconnect() {}
}


