import { Injectable } from '@angular/core';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './auth.service';

@Injectable()
export class StudentService {

    constructor (private http: HttpClient,
                private authService: AuthService) {
                }

    public checkIfStudentCanApply(projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + this.authService.currentUser.id + `/apply`, {
            params: {
                projectId: projectId,
            }
        });
    }

    public getAssignedProjectForStudent() {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/${this.authService.currentUser.id}/project`);
    }

    public addPreference(requestBody) {
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/students/` + this.authService.currentUser.id + `/preferences`, requestBody);
    }

    public getDetails(studentId, projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + studentId + '/details', {
            params: {
                projectId: projectId
            }
        });
    }

    public getPreferences() {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + this.authService.currentUser.id + `/preferences`);
    }

    public removePreference(preferenceId) {
        return this.http.delete(`${APP_CONSTANTS.ENDPOINT}/students/` + this.authService.currentUser.id + `/preferences/` + preferenceId);
    }

    public reorderPreference(body) {
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/students/` + this.authService.currentUser.id + `/preferences/reorder`, body);
    }

    public getStudentInfo(studentId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/${studentId}`);
    }
}

