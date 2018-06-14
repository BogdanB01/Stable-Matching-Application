import { Injectable, ApplicationRef } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { User } from '../interfaces/user';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { SnackBarService } from './snackbar.service';
import { UserState } from '../interfaces/user.state';
import { JwtHelper } from 'angular2-jwt';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthService {

    public role = new BehaviorSubject<string>('');
    public loggedIn = new BehaviorSubject<boolean>(false);
    currentUser;

    public jwtHelper = new JwtHelper();

    constructor(private router: Router,
        private http: HttpClient,
        private snackBarService: SnackBarService,
    ) {

        const token = localStorage.getItem('token'); // handled for page hard refresh event
        if (token != null) {
            this.decode(token);
        }
    }

    public decode(token: string) {
        const tokenPayload = this.jwtHelper.decodeToken(token);
        this.role.next(tokenPayload.role[0].name);
        if (this.jwtHelper.isTokenExpired(token)) {
            this.loggedIn.next(false);
        } else {
            this.loggedIn.next(true);
        }
        this.currentUser = tokenPayload;
    }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem('token');
        // Check whether the token is expired and return
        if (token) {
            return !this.jwtHelper.isTokenExpired(token);
        }
        return false;
    }

    getRole() {
        const token = localStorage.getItem('token');

        const tokenPayload = this.jwtHelper.decodeToken(token);

        return tokenPayload.role[0].name;
    }

    login(user: User): void {
        this.http.post(`${APP_CONSTANTS.ENDPOINT}/auth/login`, user, {responseType: 'text'}).subscribe(res => {
            console.log(res);
            if (res) {
              //  this.loggedIn.next(true);
                this.router.navigate(['/projects']);
                localStorage.setItem('token', res);
                this.decode(res);
              //  this.role.next(this.getRole());
            }
        }, err => {
            console.log(err);
            if (err.status === 401) {
                this.snackBarService.showSnackBar('Emailul si parola nu se potrivesc!');
            }
        });
    }


    logout(): void {
        localStorage.removeItem('token');
        this.loggedIn.next(false);
        this.router.navigate(['/login']);
    }

    changePassword(body) {
        this.http.post(`${APP_CONSTANTS.ENDPOINT}/auth/change-password`, body).subscribe(res => {
            this.snackBarService.showSnackBar('Parola schimbata cu succes!');
        }, err => {
            this.snackBarService.showSnackBar('Operatia nu a reusit!');
        });
    }

    shouldIGetToken(threesholdSeconds: number = 120): boolean {
        const token = localStorage.getItem('token');
        const expireDate = this.jwtHelper.getTokenExpirationDate(token).valueOf() - (threesholdSeconds * 1000);

        return new Date().valueOf() > expireDate;
    }

    refreshToken() {
        // this.http.post(`${APP_CONSTANTS.ENDPOINT}/auth/refresh`, null).subscribe(res => {
        //     console.log(res);
        // }, err => {
        //     console.log(err);
        // });
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/auth/refresh`, null, {responseType: 'text'}).do((token: string) => {
            this.clear();
            localStorage.setItem('token', token);
            this.decode(token);
        });
    }

    clear() {
        localStorage.clear();
    }
}
