import { Injectable } from '@angular/core';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient, HttpParams, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { SnackBarService } from './snackbar.service';

@Injectable()
export class ProjectService {

    dataChange: BehaviorSubject<any> = new BehaviorSubject<any>([]);
    lecturerId = 31;

    constructor(private http: HttpClient,
                private snackBarService: SnackBarService) {}

    get data(): any {
        return this.dataChange.value;
    }

    public getProjects(pageNumber: any, pageSize: any, type: any, filter: any): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/projects`, {
            params: {
                type: type,
                filter: filter,
                page: pageNumber,
                size: pageSize
            }
        });
    }

    public createProject(project: any): Observable<any> {
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/lecturers/` + this.lecturerId + `/projects`, project);
    }

    public updateProjects(projects: any): Observable<any> {
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/lecturers/` + this.lecturerId + `/projects`, projects);
    }

    public getActiveProjectsForLecturer(): void {

        this.http.get(`${APP_CONSTANTS.ENDPOINT}/lecturers/` + this.lecturerId + `/activeProjects`).subscribe(data => {
            this.dataChange.next(data);
        },
        (error: HttpErrorResponse) => {
            console.log(error.name + ' ' + error.message);
        });
    }

    public getInactiveProjectsForLecturer(): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/lecturers/` + this.lecturerId + `/inactiveProjects`);
    }

    public getProject(id: number): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/projects/` + id);
    }

    public deleteProject(id: number) {
        this.http.delete(`${APP_CONSTANTS.ENDPOINT}/projects/` + id).subscribe(data => {
            this.dataChange.next(this.dataChange.value);
            this.snackBarService.showSnackBar('Proiectul a fost sters!');
        },
        (error: HttpErrorResponse) => {
            console.log('eroare');
            this.snackBarService.showSnackBar('Proiectul nu a putut fi sters!');
        });
    }

    public updateProject(project: any): Observable<any> {
        console.log(project);
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/projects/` + project.id, project);
    }

    public assignStudent(projectId, studentName) {
        console.log(projectId);
        return this.http.patch(`${APP_CONSTANTS.ENDPOINT}/projects/` + projectId + '/assign',
            {name: studentName}
        );
    }

    public unassignStudent(projectId, studentId) {
        return this.http.patch(`${APP_CONSTANTS.ENDPOINT}/projects/` + projectId + '/delete',
            {id: studentId}
        );
    }

    public getProjectPreferences(projectId) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/projects/` + projectId + '/preferences');
    }

    public reorderPreferences(preferences, projectId) {
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/projects/` + projectId + '/preferences', preferences);
    }


}
