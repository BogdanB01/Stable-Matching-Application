import { Injectable } from '@angular/core';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class StudentService {

    studentId = 21;
    constructor (private http: HttpClient) {}

    getStudentByName(name: string) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/filter?name=` + name);
    }

    public checkIfStudentCanApply(projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + this.studentId + `/apply`, {
            params: {
                projectId: projectId,
            }
        });
    }

    public getAssignedProjectForStudent() {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/${this.studentId}/project`);
    }

    public addPreference(requestBody) {
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/students/` + this.studentId + `/preferences`, requestBody);
    }

    public getDetails(studentId, projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + studentId + '/details', {
            params: {
                projectId: projectId
            }
        });
    }

    public getPreferences() {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/` + this.studentId + `/preferences`);
    }

    public removePreference(preferenceId) {
        return this.http.delete(`${APP_CONSTANTS.ENDPOINT}/students/` + this.studentId + `/preferences/` + preferenceId);
    }

    public reorderPreference(body) {
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/students/` + this.studentId + `/preferences/reorder`, body);
    }
}

