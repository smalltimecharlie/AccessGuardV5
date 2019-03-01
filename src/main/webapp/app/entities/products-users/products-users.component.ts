import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductsUsers } from 'app/shared/model/products-users.model';
import { AccountService } from 'app/core';
import { ProductsUsersService } from './products-users.service';

@Component({
    selector: 'jhi-products-users',
    templateUrl: './products-users.component.html'
})
export class ProductsUsersComponent implements OnInit, OnDestroy {
    productsUsers: IProductsUsers[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected productsUsersService: ProductsUsersService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.productsUsersService
            .query()
            .pipe(
                filter((res: HttpResponse<IProductsUsers[]>) => res.ok),
                map((res: HttpResponse<IProductsUsers[]>) => res.body)
            )
            .subscribe(
                (res: IProductsUsers[]) => {
                    this.productsUsers = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductsUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductsUsers) {
        return item.id;
    }

    registerChangeInProductsUsers() {
        this.eventSubscriber = this.eventManager.subscribe('productsUsersListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
