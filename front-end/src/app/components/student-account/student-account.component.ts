import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../shared/services/student.service';
import { MatDialog } from '@angular/material';
import { DeleteDialogComponent } from '../../dialogs/delete/delete.dialog.component';
import { SnackBarService } from '../../shared/services/snackbar.service';

@Component({
  selector: 'app-student-account',
  templateUrl: './student-account.component.html',
  styleUrls: ['./student-account.component.sass']
})
export class StudentAccountComponent implements OnInit {


  draggable = false;
  preferences = null;
  project = null;

  constructor(
    public dialog: MatDialog,
    private studentService: StudentService,
    private snackBarService: SnackBarService
  ) { }

  ngOnInit() {
    this.studentService.getPreferences().subscribe(res => {
      this.preferences = res;
      console.log(res);
    }, err => {
      console.log('eroare');
    });

    this.studentService.getAssignedProjectForStudent().subscribe(res => {
      this.project = res;
      console.log(res);
    });
  }

  removeProject(index: number): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '350px',
      data: {
        title: this.preferences[index].project.title
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.studentService.removePreference(this.preferences[index].id).subscribe(res => {
          this.preferences.splice(index, 1);
          this.snackBarService.showSnackBar('Preferinta a fost stearsa cu succes!');
        }, err => {
          this.snackBarService.showSnackBar('A intervenit o eroare la stergerea preferintei!');
        });
      }
    });
  }

  saveOrder(): void {
    console.log(this.preferences);
    this.studentService.reorderPreference(this.preferences).subscribe(res => {
      console.log(res);
    }, err => {
      console.log(err);
    });
  }
}

