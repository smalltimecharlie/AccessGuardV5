import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocationType } from 'app/shared/model/location-type.model';
import { AccountService } from 'app/core';
import { LocationTypeService } from './location-type.service';

@Component({
    selector: 'jhi-location-type',
    templateUrl: './location-type.component.html'
})
export class LocationTypeComponent implements OnInit, OnDestroy {
    locationTypes: ILocationType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected locationTypeService: LocationTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.locationTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<ILocationType[]>) => res.ok),
                map((res: HttpResponse<ILocationType[]>) => res.body)
            )
            .subscribe(
                (res: ILocationType[]) => {
                    this.locationTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocationTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILocationType) {
        return item.id;
    }

    registerChangeInLocationTypes() {
        this.eventSubscriber = this.eventManager.subscribe('locationTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
