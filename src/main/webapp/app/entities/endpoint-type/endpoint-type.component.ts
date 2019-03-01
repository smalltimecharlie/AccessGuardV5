import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEndpointType } from 'app/shared/model/endpoint-type.model';
import { AccountService } from 'app/core';
import { EndpointTypeService } from './endpoint-type.service';

@Component({
    selector: 'jhi-endpoint-type',
    templateUrl: './endpoint-type.component.html'
})
export class EndpointTypeComponent implements OnInit, OnDestroy {
    endpointTypes: IEndpointType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected endpointTypeService: EndpointTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.endpointTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IEndpointType[]>) => res.ok),
                map((res: HttpResponse<IEndpointType[]>) => res.body)
            )
            .subscribe(
                (res: IEndpointType[]) => {
                    this.endpointTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEndpointTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEndpointType) {
        return item.id;
    }

    registerChangeInEndpointTypes() {
        this.eventSubscriber = this.eventManager.subscribe('endpointTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
