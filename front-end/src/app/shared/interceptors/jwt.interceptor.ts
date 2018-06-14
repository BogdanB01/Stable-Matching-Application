import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent,
         HttpInterceptor, HttpErrorResponse, HttpSentEvent,
         HttpHeaderResponse, HttpProgressEvent, HttpResponse, HttpUserEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AuthService } from '../services/auth.service';
import { JwtHelper } from 'angular2-jwt';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) {}

    jwtHelper = new JwtHelper();

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        const token = localStorage.getItem('token');
        if (token) {

            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }
        return next.handle(request);

    }

    handle400Error(error) {
        return Observable.throw(error);
    }

    handle401Error(req: HttpRequest<any>, next: HttpHandler) {

    }
}
