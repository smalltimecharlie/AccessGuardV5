import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductsUsers } from 'app/shared/model/products-users.model';

@Component({
    selector: 'jhi-products-users-detail',
    templateUrl: './products-users-detail.component.html'
})
export class ProductsUsersDetailComponent implements OnInit {
    productsUsers: IProductsUsers;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productsUsers }) => {
            this.productsUsers = productsUsers;
        });
    }

    previousState() {
        window.history.back();
    }
}
