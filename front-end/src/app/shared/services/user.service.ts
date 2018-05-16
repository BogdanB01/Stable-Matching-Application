import { Injectable } from '@angular/core';
import { APP_CONSTANTS } from '../app.constants';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserService {

    constructor(private http: HttpClient) {}

    getUsers(page, size, filter = '') {
        return this.http.get(`${APP_CONSTANTS.ENDPOINT}/users/filter`, {
            params: {
                email: filter,
                page: page,
                size: size
            }
        }
     );
    }

    deleteUser(id: number) {
        return this.http.delete(`${APP_CONSTANTS.ENDPOINT}/users/` + id);
    }
}
