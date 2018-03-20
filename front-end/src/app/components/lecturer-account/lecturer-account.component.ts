import { Component, OnInit, Inject } from '@angular/core';
import { MatTableDataSource, MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EditProjectDialogComponent } from '../edit-project-dialog/edit-project-dialog.component';
import { CreateProjectComponent } from '../create-project/create-project.component';

@Component({
  selector: 'app-lecturer-account',
  templateUrl: './lecturer-account.component.html',
  styleUrls: ['./lecturer-account.component.sass']
})
export class LecturerAccountComponent {

  removable = true;
  displayedColumns = ['title', 'capacity', 'assignedStudents', 'actions'];
  dataSource = ELEMENT_DATA;
  searchStudent: any;
  project: any;

  constructor(public dialog: MatDialog) {}

  remove(projectIndex: number, studentIndex: number): void {
    this.dataSource[projectIndex].assignedStudents.splice(studentIndex, 1);
  }

  openSearchStudentModal(): void {
    const dialogRef = this.dialog.open(SearchStudentDialogComponent, {
      width: '350px',
      data: {student: this.searchStudent}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openEditProjectModal(): void {
    const dialogRef = this.dialog.open(EditProjectDialogComponent, {
      width: '600px',
      data: {project: this.project}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The edit project modal was closed!');
    });
  }
}



export interface Element {
  title: string;
  capacity: number;
  assignedStudents: Array<Student>;
}

export interface Student {
  name: string;
}

const ELEMENT_DATA: Element[] = [
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}, {name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Baze de date de tip graf', capacity: 1, assignedStudents : [{name: 'Olaru'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]},
  {title: 'Aplicatii ale problemelor Stable Matching', capacity: 2, assignedStudents : [{name: 'Boca'}]}
];


@Component({
  selector: 'app-search-student-dialog',
  templateUrl: 'search-student-dialog.html',
  styleUrls: ['./search-student-dialog.sass']
})
export class SearchStudentDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<SearchStudentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
