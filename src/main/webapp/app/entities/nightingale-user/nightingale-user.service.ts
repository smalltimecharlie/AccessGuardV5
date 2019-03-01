import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INightingaleUser } from 'app/shared/model/nightingale-user.model';

type EntityResponseType = HttpResponse<INightingaleUser>;
type EntityArrayResponseType = HttpResponse<INightingaleUser[]>;

@Injectable({ providedIn: 'root' })
export class NightingaleUserService {
    public resourceUrl = SERVER_API_URL + 'api/nightingale-users';

    constructor(protected http: HttpClient) {}

    create(nightingaleUser: INightingaleUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(nightingaleUser);
        return this.http
            .post<INightingaleUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(nightingaleUser: INightingaleUser): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(nightingaleUser);
        return this.http
            .put<INightingaleUser>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<INightingaleUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<INightingaleUser[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(nightingaleUser: INightingaleUser): INightingaleUser {
        const copy: INightingaleUser = Object.assign({}, nightingaleUser, {
            cognitoSignupDate:
                nightingaleUser.cognitoSignupDate != null && nightingaleUser.cognitoSignupDate.isValid()
                    ? nightingaleUser.cognitoSignupDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.cognitoSignupDate = res.body.cognitoSignupDate != null ? moment(res.body.cognitoSignupDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((nightingaleUser: INightingaleUser) => {
                nightingaleUser.cognitoSignupDate =
                    nightingaleUser.cognitoSignupDate != null ? moment(nightingaleUser.cognitoSignupDate) : null;
            });
        }
        return res;
    }
}
