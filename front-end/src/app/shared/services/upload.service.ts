import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONSTANTS } from '../app.constants';
import { saveAs } from 'file-saver/FileSaver';
import { SnackBarService } from './snackbar.service';

@Injectable()
export class UploadService {

    constructor(private httpClient: HttpClient,
                private snackBarService: SnackBarService) {}


    matchStudentsToProjectsAndGetReport() {
        this.httpClient.get(`${APP_CONSTANTS.ENDPOINT}/api/report`, {
            responseType: 'blob', observe: 'response'
        }).subscribe(res => {
            saveAs(res.body, 'report.pdf');
        }, err => {
            console.log(err);
        });
    }

    pushFileToStorage(file: File): Observable<any> {
        const formdata: FormData = new FormData();

        formdata.append('file', file);

        const req = new HttpRequest('POST', `${APP_CONSTANTS.ENDPOINT}/upload`, formdata, {
            reportProgress: false,
            responseType: 'text'
        });

        return this.httpClient.request(req);
    }

    uploadFile(file: File, type: string) {
        const formdata: FormData = new FormData();

        formdata.append('file', file);
        formdata.append('type', type);
        const req = new HttpRequest('POST', `${APP_CONSTANTS.ENDPOINT}/upload/entities`, formdata, {
            reportProgress: false
        });

        this.httpClient.request(req).subscribe(res => {
            this.snackBarService.showSnackBar('Fisierul a fost uploadat cu succes!');
        }, err => {
            this.snackBarService.showSnackBar('Fisierul nu a putut fi uploadat!');
        });
    }

    deleteFile(filename: string) {

        return this.httpClient.delete(`${APP_CONSTANTS.ENDPOINT}/upload/` + filename);
    }

    getFile(filename: string) {

        return this.httpClient.get(`${APP_CONSTANTS.ENDPOINT}/upload/` + filename, {
            responseType: 'blob', observe: 'response'
        }).subscribe(res => {
            const contentDisposition = res.headers.get('content-disposition') || '';
            const matches = /filename=([^;]+)/ig.exec(contentDisposition);
            const file = (matches[1] || 'untitled').trim();

            saveAs(res.body, filename);
        }, err => {
            console.log('Error downloading file!');
        });
    }
}
