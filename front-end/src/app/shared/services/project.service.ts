import { Injectable } from '@angular/core';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient, HttpParams, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';


@Injectable()
export class ProjectService {

    dataChange: BehaviorSubject<any> = new BehaviorSubject<any>([]);

    constructor(private http: HttpClient) {}

    get data(): any {
        return this.dataChange.value;
    }

    public getProjects(pageNumber: any, pageSize: any): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/projects`, {
            params: {
                page: pageNumber,
                size: pageSize
            }
        });
    }

    public createProject(project: any): Observable<any> {
        return this.http.post(`${APP_CONSTANTS.ENDPOINT}/lecturers/300/projects`, project);
    }

    public getProjectsForLecturer(): void {

        this.http.get(`${APP_CONSTANTS.ENDPOINT}/lecturers/300/projects`).subscribe(data => {
            this.dataChange.next(data);
        },
        (error: HttpErrorResponse) => {
            console.log(error.name + ' ' + error.message);
        });
    }

    public getProject(id: number): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/projects/` + id);
    }

    public deleteProject(id: number) {
        this.http.delete(`${APP_CONSTANTS.ENDPOINT}/projects/` + id).subscribe(data => {
            this.dataChange.next(this.dataChange.value);
        },
        (error: HttpErrorResponse) => {
            console.log('eroare');
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

}
