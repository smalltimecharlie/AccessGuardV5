import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';

type EntityResponseType = HttpResponse<IOrganisationEndpoint>;
type EntityArrayResponseType = HttpResponse<IOrganisationEndpoint[]>;

@Injectable({ providedIn: 'root' })
export class OrganisationEndpointService {
    public resourceUrl = SERVER_API_URL + 'api/organisation-endpoints';

    constructor(protected http: HttpClient) {}

    create(organisationEndpoint: IOrganisationEndpoint): Observable<EntityResponseType> {
        return this.http.post<IOrganisationEndpoint>(this.resourceUrl, organisationEndpoint, { observe: 'response' });
    }

    update(organisationEndpoint: IOrganisationEndpoint): Observable<EntityResponseType> {
        return this.http.put<IOrganisationEndpoint>(this.resourceUrl, organisationEndpoint, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrganisationEndpoint>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrganisationEndpoint[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
