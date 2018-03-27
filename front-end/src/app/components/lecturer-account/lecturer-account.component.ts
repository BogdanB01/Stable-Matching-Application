import { Component, OnInit, Inject } from '@angular/core';
import { MatTableDataSource, MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTab } from '@angular/material';
import { EditProjectDialogComponent } from '../edit-project-dialog/edit-project-dialog.component';
import { CreateProjectComponent } from '../create-project/create-project.component';
import { StatisticsDialogComponent } from '../lecturer-project-statistics-dialog/statistics-dialog-component';

@Component({
  selector: 'app-lecturer-account',
  templateUrl: './lecturer-account.component.html',
  styleUrls: ['./lecturer-account.component.sass']
})
export class LecturerAccountComponent {

  removable = true;
  displayedColumns = ['title', 'capacity', 'assignedStudents', 'actions'];
  dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);
  searchStudent: any;
  project: any;

  constructor(public dialog: MatDialog) {}

  remove(projectIndex: number, studentIndex: number): void {
    this.dataSource.data[projectIndex].assignedStudents.splice(studentIndex, 1);
  }

  removeProject(index: number): void {
    this.dataSource.data.splice(index, 1);
    this.dataSource = new MatTableDataSource<Element>(this.dataSource.data);
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

  openEditProjectModal(index: any): void {
    const dialogRef = this.dialog.open(EditProjectDialogComponent, {
      width: '600px',
      data: {project: this.dataSource.data[index]}
    });

    const sub = dialogRef.componentInstance.onUpdate.subscribe((data) => {
      // temp hack

      this.dataSource.data[index].title = data.title;
      this.dataSource.data[index].capacity = data.capacity;
      this.dataSource.data[index].description = data.description;
      this.dataSource.data[index].tags = data.tags;
      this.dataSource.data[index].references = data.tags;

      this.dataSource = new MatTableDataSource<Element>(this.dataSource.data);
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

export interface Element {
  title: string;
  capacity: number;
  assignedStudents: Array<Student>;
  tags: Array<any>;
  references: Array<any>;
  description: String;
}

export interface Student {
  name: string;
}

const ELEMENT_DATA: Element[] = [
  {
    title: 'Aplicatii ale problemelor Stable', tags : ['web', 'java'], references: ['www.google.com', 'www.facebook.com'],
    capacity: 2, assignedStudents : [{name: 'Boca'}, {name: 'Boca'}], description : 'ce proiect frumos'
  },
  {
    title: 'Baze de date de tip graf', tags : ['web', 'java', 'graphdatabase'], references: ['www.neo4j.com', 'www.facebook.com'],
    capacity: 2, assignedStudents : [{name: 'olaru'}, {name: 'marius'}], description: 'Trebuie sa iti placa bazele de date'
  },
  {
    title: 'Criptografie avansata', tags : ['mate', 'cripto'], references: ['www.wikipedia.com', 'www.facebook.com'],
    capacity: 1, assignedStudents : [], description: 'Sunt necesare cunostinte de matematica avansate'
  },
  {
    title: 'Stable matching', tags : ['web', 'java', 'java8'], references: ['www.google.com', 'www.aaaa.com'],
    capacity: 3, assignedStudents : [{name: 'Boca'}, {name: 'Boca'}], description: 'se va folosi algoritmul lui gale shapley'
  },
  {
    title: 'Partajarea secretelor', tags : ['cripto', 'math'], references: ['www.wiki.com', 'www.flt.com'],
    capacity: 2, assignedStudents : [{name: 'Boca'}, {name: 'Boca'}], description: 'descrierea proiectului de partajare a secretelor'
  },
  {
    title: 'Proiect de licenta', tags : ['tag1', 'tag2'], references: ['www.site1.com', 'www.site2.com'],
    capacity: 1, assignedStudents : [] , description: 'Un proiect de licenta ce este foarte interesant'
  }
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
