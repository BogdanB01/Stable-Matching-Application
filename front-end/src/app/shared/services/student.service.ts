import { Injectable } from '@angular/core';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class StudentService {

    constructor (private http: HttpClient) {}

    getStudentByName(name: string) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/filter?name=` + name);
    }

    public checkIfStudentCanApply(projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/14/apply`, {
            params: {
                projectId: projectId,
            }
        });
    }

    public addPreference(request) {
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/students/14/preferences`, request);
    }
}

