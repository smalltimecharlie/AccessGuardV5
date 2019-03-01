import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgreementType } from 'app/shared/model/agreement-type.model';
import { AgreementTypeService } from './agreement-type.service';
import { AgreementTypeComponent } from './agreement-type.component';
import { AgreementTypeDetailComponent } from './agreement-type-detail.component';
import { AgreementTypeUpdateComponent } from './agreement-type-update.component';
import { AgreementTypeDeletePopupComponent } from './agreement-type-delete-dialog.component';
import { IAgreementType } from 'app/shared/model/agreement-type.model';

@Injectable({ providedIn: 'root' })
export class AgreementTypeResolve implements Resolve<IAgreementType> {
    constructor(private service: AgreementTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgreementType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AgreementType>) => response.ok),
                map((agreementType: HttpResponse<AgreementType>) => agreementType.body)
            );
        }
        return of(new AgreementType());
    }
}

export const agreementTypeRoute: Routes = [
    {
        path: '',
        component: AgreementTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgreementTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AgreementTypeDetailComponent,
        resolve: {
            agreementType: AgreementTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgreementTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AgreementTypeUpdateComponent,
        resolve: {
            agreementType: AgreementTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgreementTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AgreementTypeUpdateComponent,
        resolve: {
            agreementType: AgreementTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgreementTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agreementTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AgreementTypeDeletePopupComponent,
        resolve: {
            agreementType: AgreementTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgreementTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
