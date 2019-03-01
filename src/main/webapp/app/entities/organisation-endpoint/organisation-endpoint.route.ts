import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';
import { OrganisationEndpointService } from './organisation-endpoint.service';
import { OrganisationEndpointComponent } from './organisation-endpoint.component';
import { OrganisationEndpointDetailComponent } from './organisation-endpoint-detail.component';
import { OrganisationEndpointUpdateComponent } from './organisation-endpoint-update.component';
import { OrganisationEndpointDeletePopupComponent } from './organisation-endpoint-delete-dialog.component';
import { IOrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';

@Injectable({ providedIn: 'root' })
export class OrganisationEndpointResolve implements Resolve<IOrganisationEndpoint> {
    constructor(private service: OrganisationEndpointService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrganisationEndpoint> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OrganisationEndpoint>) => response.ok),
                map((organisationEndpoint: HttpResponse<OrganisationEndpoint>) => organisationEndpoint.body)
            );
        }
        return of(new OrganisationEndpoint());
    }
}

export const organisationEndpointRoute: Routes = [
    {
        path: '',
        component: OrganisationEndpointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrganisationEndpoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OrganisationEndpointDetailComponent,
        resolve: {
            organisationEndpoint: OrganisationEndpointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrganisationEndpoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OrganisationEndpointUpdateComponent,
        resolve: {
            organisationEndpoint: OrganisationEndpointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrganisationEndpoints'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OrganisationEndpointUpdateComponent,
        resolve: {
            organisationEndpoint: OrganisationEndpointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrganisationEndpoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const organisationEndpointPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OrganisationEndpointDeletePopupComponent,
        resolve: {
            organisationEndpoint: OrganisationEndpointResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrganisationEndpoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
