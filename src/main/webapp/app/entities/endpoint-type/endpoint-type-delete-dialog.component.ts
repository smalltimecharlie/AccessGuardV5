import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndpointType } from 'app/shared/model/endpoint-type.model';
import { EndpointTypeService } from './endpoint-type.service';

@Component({
    selector: 'jhi-endpoint-type-delete-dialog',
    templateUrl: './endpoint-type-delete-dialog.component.html'
})
export class EndpointTypeDeleteDialogComponent {
    endpointType: IEndpointType;

    constructor(
        protected endpointTypeService: EndpointTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.endpointTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'endpointTypeListModification',
                content: 'Deleted an endpointType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-endpoint-type-delete-popup',
    template: ''
})
export class EndpointTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ endpointType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EndpointTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.endpointType = endpointType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/endpoint-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/endpoint-type', { outlets: { popup: null } }]);
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
