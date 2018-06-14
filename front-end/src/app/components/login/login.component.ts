import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../shared/services/auth.service';
import { User } from '../../shared/interfaces/user';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {
    this.authService.logout();
  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      email : ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {

    const formModel = this.loginForm.value;
    const user: User = {
      email: formModel.email as string,
      password: formModel.password as string
    };
    this.authService.login(user);
  }

}
