import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EndpointType } from 'app/shared/model/endpoint-type.model';
import { EndpointTypeService } from './endpoint-type.service';
import { EndpointTypeComponent } from './endpoint-type.component';
import { EndpointTypeDetailComponent } from './endpoint-type-detail.component';
import { EndpointTypeUpdateComponent } from './endpoint-type-update.component';
import { EndpointTypeDeletePopupComponent } from './endpoint-type-delete-dialog.component';
import { IEndpointType } from 'app/shared/model/endpoint-type.model';

@Injectable({ providedIn: 'root' })
export class EndpointTypeResolve implements Resolve<IEndpointType> {
    constructor(private service: EndpointTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEndpointType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EndpointType>) => response.ok),
                map((endpointType: HttpResponse<EndpointType>) => endpointType.body)
            );
        }
        return of(new EndpointType());
    }
}

export const endpointTypeRoute: Routes = [
    {
        path: '',
        component: EndpointTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EndpointTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EndpointTypeDetailComponent,
        resolve: {
            endpointType: EndpointTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EndpointTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EndpointTypeUpdateComponent,
        resolve: {
            endpointType: EndpointTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EndpointTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EndpointTypeUpdateComponent,
        resolve: {
            endpointType: EndpointTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EndpointTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const endpointTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EndpointTypeDeletePopupComponent,
        resolve: {
            endpointType: EndpointTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EndpointTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
