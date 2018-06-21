import { Component, OnInit, Input } from '@angular/core';
import { Project } from '../../shared/interfaces/project';
import { StudentService } from '../../shared/services/student.service';
import { MatDialog } from '@angular/material';
import { ApplyDialogComponent } from '../apply-dialog/apply-dialog.component';
import { SnackBarService } from '../../shared/services/snackbar.service';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.sass']
})
export class ProjectCardComponent implements OnInit {
  @Input() project;
  color: string;
  div: any;

  constructor(private studentService: StudentService,
              private dialog: MatDialog,
              private snackBarService: SnackBarService,
              private authService: AuthService) {
    // this.div = document.createElement('div');
    // this.div.innerHTML = this.project.description;
  }

  ngOnInit() {
    this.div = document.createElement('div');
    this.div.innerHTML = this.project.description;
    this.div.innerText = this.div.innerText.substring(0, 64);
  }

  apply() {
    this.studentService.checkIfStudentCanApply(this.project.id).subscribe(res => {
      if (res === true) {
        if (this.project.questions !== null) {
          const dialogRef = this.dialog.open(ApplyDialogComponent, {
            width: '600px',
            data: {
              title: this.project.title,
              projectId: this.project.id,
              questions: this.project.questions
            }
          });
        }
      } else {
        this.snackBarService.showSnackBar('Nu poti aplica de 2 ori la acelasi proiect!');
      }
    }, err => {
      if (err.status === 400) {
        this.snackBarService.showSnackBar(err.error.message);
      }
      console.log(err);
    });
  }
}
