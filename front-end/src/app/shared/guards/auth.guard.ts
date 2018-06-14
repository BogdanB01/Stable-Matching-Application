import { Injectable, Inject } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/take';
import 'rxjs/add/operator/map';
import { mapTo } from 'rxjs/operators';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(
        private authService: AuthService,
        private router: Router
    ) {}

    canActivate(next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        return this.performCheck(next, state);
    }

    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|Promise<boolean>|boolean {
        return this.performCheck(childRoute, state);
    }

    private performCheck(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
        if (this.authService.isAuthenticated()) {
            // let's check if the token will expire soon
            if (this.authService.shouldIGetToken()) {
                console.log('vreau sa fac refresh');
              return this.authService.refreshToken()
                .pipe(mapTo(true)); // resolve to true since we are already handling it before now, just return true
            } else {
              return true;
            }
        }

        this.authService.logout();
        this.router.navigate(['/login']);
        return false;
    }
}
