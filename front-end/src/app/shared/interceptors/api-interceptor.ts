import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpResponse
} from '@angular/common/http';

import { HttpErrorResponse } from '@angular/common/http';
import { LoaderService } from '../../components/loader/loader.service';

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
    constructor(private loaderService: LoaderService) {}
    private count = 0;

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.count = this.count + 1;

        if (this.count === 1) {
            this.loaderService.show();
        }
        return next.handle(req).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
              // do stuff with response if you want
              this.count = this.count - 1;

              if (this.count ===  0) {
              //  this.loaderService.hide();
                    this.loaderService.hide();
              }
            }
          }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
              this.count = this.count - 1;
              this.loaderService.hide();
            //  return Observable.of(err);
            }
          });
    }

}
