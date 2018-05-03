import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONSTANTS } from '../app.constants';

@Injectable()
export class SearchService {

    constructor(private http: HttpClient) {}

    searchStudent(term): Observable<any> {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/students/?search=` + term);
    }
}
