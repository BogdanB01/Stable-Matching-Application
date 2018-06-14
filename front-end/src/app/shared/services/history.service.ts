import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { APP_CONSTANTS } from '../app.constants';
import { Observable } from 'rxjs/Observable';
import { saveAs } from 'file-saver/FileSaver';
import { SnackBarService } from './snackbar.service';

@Injectable()
export class HistoryService {

    constructor(private http: HttpClient,
                private snackBarService: SnackBarService) {}

    getYears(): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/admin/years`);
    }

    getReportForYear(year: number) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/admin/${year}`,
                            {responseType: 'blob',  observe: 'response'})
        .subscribe(res => {
            saveAs(res.body, `${year}.pdf`);
        }, err => {
            console.log('Error downloading file!');
        });
    }

    getApplicationStatus(): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/admin/app/state`);
    }

    setApplicationStatus(status): Observable<any> {
        return this.http.put(`${APP_CONSTANTS.ENDPOINT}/admin/app/state`, {status: status});
    }

    saveMatches() {
        this.http.get(`${APP_CONSTANTS.ENDPOINT}/admin/save`).subscribe(res => {
            this.snackBarService.showSnackBar('Situatie salvata cu succes!');
        }, err => {
            if (err.status === 400) {
                console.log(err);
                this.snackBarService.showSnackBar(err.error.message);
            }
        });
    }

    matchStudentsToProjectsAndGetReport() {
        this.http.get(`${APP_CONSTANTS.ENDPOINT}/admin/report`, {
            responseType: 'blob', observe: 'response'
        }).subscribe(res => {
            saveAs(res.body, 'report.pdf');
        }, err => {
            console.log(err.status);
            if (err.status === 400) {
                this.snackBarService.showSnackBar('Proiectele pentru acest an au fost repartizate!');
            } else {
                this.snackBarService.showSnackBar('A intervenit o eroare!');
            }
        });
    }
}
