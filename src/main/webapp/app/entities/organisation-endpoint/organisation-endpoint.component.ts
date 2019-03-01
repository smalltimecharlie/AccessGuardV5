import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';
import { AccountService } from 'app/core';
import { OrganisationEndpointService } from './organisation-endpoint.service';

@Component({
    selector: 'jhi-organisation-endpoint',
    templateUrl: './organisation-endpoint.component.html'
})
export class OrganisationEndpointComponent implements OnInit, OnDestroy {
    organisationEndpoints: IOrganisationEndpoint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected organisationEndpointService: OrganisationEndpointService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.organisationEndpointService
            .query()
            .pipe(
                filter((res: HttpResponse<IOrganisationEndpoint[]>) => res.ok),
                map((res: HttpResponse<IOrganisationEndpoint[]>) => res.body)
            )
            .subscribe(
                (res: IOrganisationEndpoint[]) => {
                    this.organisationEndpoints = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrganisationEndpoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrganisationEndpoint) {
        return item.id;
    }

    registerChangeInOrganisationEndpoints() {
        this.eventSubscriber = this.eventManager.subscribe('organisationEndpointListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
