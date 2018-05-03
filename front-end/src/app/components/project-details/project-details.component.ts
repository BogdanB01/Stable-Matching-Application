import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectDetailsResolve } from '../../shared/services/project.resolve.service';
import { UploadService } from '../../shared/services/upload.service';
import { ApplyDialogComponent } from '../apply-dialog/apply-dialog.component';
import { MatDialog } from '@angular/material';
import { StudentService } from '../../shared/services/student.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.sass']
})
export class ProjectDetailsComponent implements OnInit {

  private project: any;
  constructor(
    private route: ActivatedRoute,
    private uploadService: UploadService,
    private studentService: StudentService,
    public dialog: MatDialog) {
      this.project = this.route.snapshot.data.message;
      console.log(this.project);
  }

  ngOnInit() {

  }


  applyToProject() {
    this.studentService.checkIfStudentCanApply(this.project.id).subscribe(res => {
      if (res === true) {
        if (this.project.questions !== null) {
          const dialogRef = this.dialog.open(ApplyDialogComponent, {
            width: '600px',
            data: {
              projectId: this.project.id,
              questions: this.project.questions
            }
          });
        }
      }
    }, err => {
      console.log(err);
    });
  }

  downloadFile(filename: string) {
    this.uploadService.getFile(filename);
  }
}

