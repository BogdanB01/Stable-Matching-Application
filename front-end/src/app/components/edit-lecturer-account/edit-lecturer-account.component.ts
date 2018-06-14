import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-edit-lecturer-account-component',
  templateUrl: './edit-lecturer-account.component.html',
  styleUrls: ['../create-project/create-project.component.sass']
})
export class EditLecturerAccountComponent implements OnInit {

  changePasswordForm: FormGroup;

  currentUser = null;
  constructor(private fb: FormBuilder,
              private authService: AuthService) {
                this.currentUser = authService.currentUser;
                console.log(this.currentUser);
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
