import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductsUsers } from 'app/shared/model/products-users.model';

type EntityResponseType = HttpResponse<IProductsUsers>;
type EntityArrayResponseType = HttpResponse<IProductsUsers[]>;

@Injectable({ providedIn: 'root' })
export class ProductsUsersService {
    public resourceUrl = SERVER_API_URL + 'api/products-users';

    constructor(protected http: HttpClient) {}

    create(productsUsers: IProductsUsers): Observable<EntityResponseType> {
        return this.http.post<IProductsUsers>(this.resourceUrl, productsUsers, { observe: 'response' });
    }

    update(productsUsers: IProductsUsers): Observable<EntityResponseType> {
        return this.http.put<IProductsUsers>(this.resourceUrl, productsUsers, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductsUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductsUsers[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
