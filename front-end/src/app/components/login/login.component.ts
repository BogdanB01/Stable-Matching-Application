import { Component, OnInit } from '@angular/core';
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
  submitted = false;
  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      email : ['', Validators.required],
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
