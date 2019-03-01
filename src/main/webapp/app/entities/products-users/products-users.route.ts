import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductsUsers } from 'app/shared/model/products-users.model';
import { ProductsUsersService } from './products-users.service';
import { ProductsUsersComponent } from './products-users.component';
import { ProductsUsersDetailComponent } from './products-users-detail.component';
import { ProductsUsersUpdateComponent } from './products-users-update.component';
import { ProductsUsersDeletePopupComponent } from './products-users-delete-dialog.component';
import { IProductsUsers } from 'app/shared/model/products-users.model';

@Injectable({ providedIn: 'root' })
export class ProductsUsersResolve implements Resolve<IProductsUsers> {
    constructor(private service: ProductsUsersService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductsUsers> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductsUsers>) => response.ok),
                map((productsUsers: HttpResponse<ProductsUsers>) => productsUsers.body)
            );
        }
        return of(new ProductsUsers());
    }
}

export const productsUsersRoute: Routes = [
    {
        path: '',
        component: ProductsUsersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductsUsersDetailComponent,
        resolve: {
            productsUsers: ProductsUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductsUsersUpdateComponent,
        resolve: {
            productsUsers: ProductsUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductsUsersUpdateComponent,
        resolve: {
            productsUsers: ProductsUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productsUsersPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductsUsersDeletePopupComponent,
        resolve: {
            productsUsers: ProductsUsersResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductsUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
