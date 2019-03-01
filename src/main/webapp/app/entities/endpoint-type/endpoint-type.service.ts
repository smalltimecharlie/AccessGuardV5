import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEndpointType } from 'app/shared/model/endpoint-type.model';

type EntityResponseType = HttpResponse<IEndpointType>;
type EntityArrayResponseType = HttpResponse<IEndpointType[]>;

@Injectable({ providedIn: 'root' })
export class EndpointTypeService {
    public resourceUrl = SERVER_API_URL + 'api/endpoint-types';

    constructor(protected http: HttpClient) {}

    create(endpointType: IEndpointType): Observable<EntityResponseType> {
        return this.http.post<IEndpointType>(this.resourceUrl, endpointType, { observe: 'response' });
    }

    update(endpointType: IEndpointType): Observable<EntityResponseType> {
        return this.http.put<IEndpointType>(this.resourceUrl, endpointType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEndpointType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEndpointType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
