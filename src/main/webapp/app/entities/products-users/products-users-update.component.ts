import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductsUsers } from 'app/shared/model/products-users.model';
import { ProductsUsersService } from './products-users.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products';

@Component({
    selector: 'jhi-products-users-update',
    templateUrl: './products-users-update.component.html'
})
export class ProductsUsersUpdateComponent implements OnInit {
    productsUsers: IProductsUsers;
    isSaving: boolean;

    products: IProducts[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productsUsersService: ProductsUsersService,
        protected productsService: ProductsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productsUsers }) => {
            this.productsUsers = productsUsers;
        });
        this.productsService
            .query({ filter: 'productsusers-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProducts[]>) => response.body)
            )
            .subscribe(
                (res: IProducts[]) => {
                    if (!this.productsUsers.products || !this.productsUsers.products.id) {
                        this.products = res;
                    } else {
                        this.productsService
                            .find(this.productsUsers.products.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IProducts>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IProducts>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IProducts) => (this.products = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productsUsers.id !== undefined) {
            this.subscribeToSaveResponse(this.productsUsersService.update(this.productsUsers));
        } else {
            this.subscribeToSaveResponse(this.productsUsersService.create(this.productsUsers));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductsUsers>>) {
        result.subscribe((res: HttpResponse<IProductsUsers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductsById(index: number, item: IProducts) {
        return item.id;
    }
}
