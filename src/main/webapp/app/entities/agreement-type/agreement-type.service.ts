import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgreementType } from 'app/shared/model/agreement-type.model';

type EntityResponseType = HttpResponse<IAgreementType>;
type EntityArrayResponseType = HttpResponse<IAgreementType[]>;

@Injectable({ providedIn: 'root' })
export class AgreementTypeService {
    public resourceUrl = SERVER_API_URL + 'api/agreement-types';

    constructor(protected http: HttpClient) {}

    create(agreementType: IAgreementType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(agreementType);
        return this.http
            .post<IAgreementType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(agreementType: IAgreementType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(agreementType);
        return this.http
            .put<IAgreementType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAgreementType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAgreementType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(agreementType: IAgreementType): IAgreementType {
        const copy: IAgreementType = Object.assign({}, agreementType, {
            agreementTypeStartDate:
                agreementType.agreementTypeStartDate != null && agreementType.agreementTypeStartDate.isValid()
                    ? agreementType.agreementTypeStartDate.toJSON()
                    : null,
            agreementTypeEndDate:
                agreementType.agreementTypeEndDate != null && agreementType.agreementTypeEndDate.isValid()
                    ? agreementType.agreementTypeEndDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.agreementTypeStartDate = res.body.agreementTypeStartDate != null ? moment(res.body.agreementTypeStartDate) : null;
            res.body.agreementTypeEndDate = res.body.agreementTypeEndDate != null ? moment(res.body.agreementTypeEndDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((agreementType: IAgreementType) => {
                agreementType.agreementTypeStartDate =
                    agreementType.agreementTypeStartDate != null ? moment(agreementType.agreementTypeStartDate) : null;
                agreementType.agreementTypeEndDate =
                    agreementType.agreementTypeEndDate != null ? moment(agreementType.agreementTypeEndDate) : null;
            });
        }
        return res;
    }
}
