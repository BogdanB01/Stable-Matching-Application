import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { JwtHelper } from 'angular2-jwt';

@Injectable()
export class RoleGuard implements CanActivate {

    private jwtHelper = new JwtHelper();
    constructor(public auth: AuthService, public router: Router) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {
        // this will be passed from the route config
        const expectedRole = route.data.expectedRole;

        const token = localStorage.getItem('token');
        const tokenPayload = this.jwtHelper.decodeToken(token);
        if (!this.auth.isAuthenticated() || !tokenPayload.role[0] || tokenPayload.role[0].name !== expectedRole
        ) {
            this.auth.logout();
            return false;
        }
        return true;
    }
}
