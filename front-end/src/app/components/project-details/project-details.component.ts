import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectDetailsResolve } from '../../shared/services/project.resolve.service';
import { UploadService } from '../../shared/services/upload.service';
import { ApplyDialogComponent } from '../apply-dialog/apply-dialog.component';
import { MatDialog, MatSuffix } from '@angular/material';
import { StudentService } from '../../shared/services/student.service';
import { SnackBarService } from '../../shared/services/snackbar.service';
import { ProjectService } from '../../shared/services/project.service';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.sass']
})
export class ProjectDetailsComponent implements OnInit {

  private project: any;
  private otherProjects: any;
  constructor(
    private route: ActivatedRoute,
    private uploadService: UploadService,
    private studentService: StudentService,
    private snackBarService: SnackBarService,
    private projectService: ProjectService,
    private authService: AuthService,
    public dialog: MatDialog) {

      // this.route.data.subscribe(res => {
      //   this.project = res.message;
      // });
    }

  ngOnInit() {
    this.route.data.subscribe(val => {
      this.project = val.message;
        this.projectService.getOtherProjectForLecturer(this.project.lecturer.id).subscribe(res => {
          // console.log(res);
          res = res.filter((item, idx) => {
            return item.id !== this.project.id;
          });

          if (res.length <= 5) {
            this.otherProjects = res;
          } else {

            const shuffled = res.sort(() => .5 - Math.random()); // shuffle
            this.otherProjects = shuffled.slice(0, 5); // get sub-array of first 5 elements after shuffle
          }
        }, err => {
          console.log(err);
        });
      });
  }


  applyToProject() {
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

  downloadFile(filename: string) {
    this.uploadService.getFile(filename);
  }
}

