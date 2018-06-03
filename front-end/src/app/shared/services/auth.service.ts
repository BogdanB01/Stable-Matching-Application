import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { User } from '../interfaces/user';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient } from '@angular/common/http';
import { SnackBarService } from './snackbar.service';
import { UserState } from '../interfaces/user.state';

@Injectable()
export class AuthService {
    private loggedIn = new BehaviorSubject<boolean>(false);

    get isLoggedIn() {
        return this.loggedIn.asObservable();
    }

    constructor(private router: Router,
                private http: HttpClient,
                private snackBarService: SnackBarService) {
                    console.log('aici');
                }

    login(user: User): void {

        console.log(APP_CONSTANTS.ENDPOINT);
        this.http.post(`${APP_CONSTANTS.ENDPOINT}/auth/login`, user).subscribe((res: UserState) => {
            if (res && res.accessToken) {
                this.loggedIn.next(true);
                this.router.navigate(['/projects']);
                localStorage.setItem('currentUser', JSON.stringify(res));
            }
        }, err => {
            if (err.status === 401) {
                this.snackBarService.showSnackBar('Emailul si parola nu se potrivesc!');
            }
        });
    }

    logout(): void {
        localStorage.removeItem('currentUser');
        this.loggedIn.next(false);
        this.router.navigate(['/login']);
    }
}
