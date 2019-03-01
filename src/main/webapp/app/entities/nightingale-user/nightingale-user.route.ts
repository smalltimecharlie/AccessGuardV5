import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NightingaleUser } from 'app/shared/model/nightingale-user.model';
import { NightingaleUserService } from './nightingale-user.service';
import { NightingaleUserComponent } from './nightingale-user.component';
import { NightingaleUserDetailComponent } from './nightingale-user-detail.component';
import { NightingaleUserUpdateComponent } from './nightingale-user-update.component';
import { NightingaleUserDeletePopupComponent } from './nightingale-user-delete-dialog.component';
import { INightingaleUser } from 'app/shared/model/nightingale-user.model';

@Injectable({ providedIn: 'root' })
export class NightingaleUserResolve implements Resolve<INightingaleUser> {
    constructor(private service: NightingaleUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INightingaleUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NightingaleUser>) => response.ok),
                map((nightingaleUser: HttpResponse<NightingaleUser>) => nightingaleUser.body)
            );
        }
        return of(new NightingaleUser());
    }
}

export const nightingaleUserRoute: Routes = [
    {
        path: '',
        component: NightingaleUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NightingaleUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NightingaleUserDetailComponent,
        resolve: {
            nightingaleUser: NightingaleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NightingaleUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NightingaleUserUpdateComponent,
        resolve: {
            nightingaleUser: NightingaleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NightingaleUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NightingaleUserUpdateComponent,
        resolve: {
            nightingaleUser: NightingaleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NightingaleUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nightingaleUserPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NightingaleUserDeletePopupComponent,
        resolve: {
            nightingaleUser: NightingaleUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NightingaleUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
