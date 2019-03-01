import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { IProductsUsers } from 'app/shared/model/products-users.model';
import { ProductsUsersService } from 'app/entities/products-users';

@Component({
    selector: 'jhi-products-update',
    templateUrl: './products-update.component.html'
})
export class ProductsUpdateComponent implements OnInit {
    products: IProducts;
    isSaving: boolean;

    productsusers: IProductsUsers[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productsService: ProductsService,
        protected productsUsersService: ProductsUsersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ products }) => {
            this.products = products;
        });
        this.productsUsersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductsUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductsUsers[]>) => response.body)
            )
            .subscribe((res: IProductsUsers[]) => (this.productsusers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.products.id !== undefined) {
            this.subscribeToSaveResponse(this.productsService.update(this.products));
        } else {
            this.subscribeToSaveResponse(this.productsService.create(this.products));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducts>>) {
        result.subscribe((res: HttpResponse<IProducts>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductsUsersById(index: number, item: IProductsUsers) {
        return item.id;
    }
}
