import { Injectable } from '@angular/core';

import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { ProjectService } from './project.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/empty';
@Injectable()
export class ProjectDetailsResolve implements Resolve<any> {

    constructor(private projectService: ProjectService) {
    }

    resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any> {
        const id = +route.params['id'];
        return this.projectService.getProject(id).catch( res => {
           // return Observable.of('data not available at this time');

            if (res.status === 404) {
                console.log('not found! redirect to 404');
            } else {
                return Observable.empty();
            }
        });
    }
}
