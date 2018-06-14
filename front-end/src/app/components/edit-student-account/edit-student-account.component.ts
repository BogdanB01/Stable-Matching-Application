import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../shared/services/auth.service';
import { StudentService } from '../../shared/services/student.service';
@Component({
  selector: 'app-edit-student-account',
  templateUrl: './edit-student-account.component.html',
  styleUrls: ['../create-project/create-project.component.sass', './edit-student-account.component.sass']
})
export class EditStudentAccountComponent implements OnInit {

  changePasswordForm: FormGroup;

  currentUser = null;
  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private studentService: StudentService) {
                console.log(authService.currentUser);
                this.studentService.getStudentInfo(authService.currentUser.id).subscribe(res => {
                  this.currentUser = res;
                }, err => {
                  console.log(err);
                });
              }

  ngOnInit() {
    this.changePasswordForm = this.fb.group({
      oldPassword : ['', Validators.required],
      newPassword: ['', [
                          Validators.required,
                          Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')
                        ]
                   ]
    });
  }

  changePassword() {
    const model = this.changePasswordForm.value;
    const body = {
      oldPassword: model.oldPassword as string,
      newPassword: model.newPassword as string
    };
    this.authService.changePassword(body);
  }
}
