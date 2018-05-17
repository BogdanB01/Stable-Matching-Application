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
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/11/apply`, {
            params: {
                projectId: projectId,
            }
        });
    }

    public addPreference(projectId) {
        console.log(projectId);
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/students/11/preferences`, null,  {
            params: {
                projectId: projectId
            }
        });
    }

    public getDetails(studentId, projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + studentId + '/details', {
            params: {
                projectId: projectId
            }
        });
    }

    public getPreferences() {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/11/preferences`);
    }

    public removePreference(preferenceId) {
        return this.http.delete(`${APP_CONSTANTS.ENDPOINT}/students/11/preferences/` + preferenceId);
    }

    public reorderPreference(body) {
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/students/11/preferences/reorder`, body);
    }
}

