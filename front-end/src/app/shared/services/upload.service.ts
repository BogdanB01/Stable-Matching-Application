import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONSTANTS } from '../app.constants';
import { saveAs } from 'file-saver/FileSaver';

@Injectable()
export class UploadService {

    constructor(private httpClient: HttpClient) {}

    pushFileToStorage(file: File): Observable<any> {
        const formdata: FormData = new FormData();

        formdata.append('file', file);

        const req = new HttpRequest('POST', `${APP_CONSTANTS.ENDPOINT}/upload`, formdata, {
            reportProgress: false,
            responseType: 'text'
        });

        return this.httpClient.request(req);
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
