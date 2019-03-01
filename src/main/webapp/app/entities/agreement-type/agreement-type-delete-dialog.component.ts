import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgreementType } from 'app/shared/model/agreement-type.model';
import { AgreementTypeService } from './agreement-type.service';

@Component({
    selector: 'jhi-agreement-type-delete-dialog',
    templateUrl: './agreement-type-delete-dialog.component.html'
})
export class AgreementTypeDeleteDialogComponent {
    agreementType: IAgreementType;

    constructor(
        protected agreementTypeService: AgreementTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agreementTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'agreementTypeListModification',
                content: 'Deleted an agreementType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-agreement-type-delete-popup',
    template: ''
})
export class AgreementTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ agreementType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AgreementTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.agreementType = agreementType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/agreement-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/agreement-type', { outlets: { popup: null } }]);
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
