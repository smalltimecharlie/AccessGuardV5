import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAgreementType } from 'app/shared/model/agreement-type.model';
import { AccountService } from 'app/core';
import { AgreementTypeService } from './agreement-type.service';

@Component({
    selector: 'jhi-agreement-type',
    templateUrl: './agreement-type.component.html'
})
export class AgreementTypeComponent implements OnInit, OnDestroy {
    agreementTypes: IAgreementType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected agreementTypeService: AgreementTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.agreementTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IAgreementType[]>) => res.ok),
                map((res: HttpResponse<IAgreementType[]>) => res.body)
            )
            .subscribe(
                (res: IAgreementType[]) => {
                    this.agreementTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAgreementTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAgreementType) {
        return item.id;
    }

    registerChangeInAgreementTypes() {
        this.eventSubscriber = this.eventManager.subscribe('agreementTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
