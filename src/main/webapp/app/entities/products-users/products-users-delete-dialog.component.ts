import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductsUsers } from 'app/shared/model/products-users.model';
import { ProductsUsersService } from './products-users.service';

@Component({
    selector: 'jhi-products-users-delete-dialog',
    templateUrl: './products-users-delete-dialog.component.html'
})
export class ProductsUsersDeleteDialogComponent {
    productsUsers: IProductsUsers;

    constructor(
        protected productsUsersService: ProductsUsersService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productsUsersService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productsUsersListModification',
                content: 'Deleted an productsUsers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-products-users-delete-popup',
    template: ''
})
export class ProductsUsersDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productsUsers }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductsUsersDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productsUsers = productsUsers;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/products-users', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/products-users', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
