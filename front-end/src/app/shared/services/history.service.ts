import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { APP_CONSTANTS } from '../app.constants';
import { Observable } from 'rxjs/Observable';
import { saveAs } from 'file-saver/FileSaver';


@Injectable()
export class HistoryService {

    constructor(private http: HttpClient) {}

    getYears(): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/history/years`);
    }

    getReportForYear(year: number) {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/history/${year}`,
                            {responseType: 'blob',  observe: 'response'})
        .subscribe(res => {
            saveAs(res.body, `${year}.pdf`);
        }, err => {
            console.log('Error downloading file!');
        });
    }
}
