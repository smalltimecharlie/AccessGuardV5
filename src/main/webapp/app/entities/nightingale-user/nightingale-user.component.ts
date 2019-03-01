import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { INightingaleUser } from 'app/shared/model/nightingale-user.model';
import { AccountService } from 'app/core';
import { NightingaleUserService } from './nightingale-user.service';

@Component({
    selector: 'jhi-nightingale-user',
    templateUrl: './nightingale-user.component.html'
})
export class NightingaleUserComponent implements OnInit, OnDestroy {
    nightingaleUsers: INightingaleUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected nightingaleUserService: NightingaleUserService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.nightingaleUserService
            .query()
            .pipe(
                filter((res: HttpResponse<INightingaleUser[]>) => res.ok),
                map((res: HttpResponse<INightingaleUser[]>) => res.body)
            )
            .subscribe(
                (res: INightingaleUser[]) => {
                    this.nightingaleUsers = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNightingaleUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INightingaleUser) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInNightingaleUsers() {
        this.eventSubscriber = this.eventManager.subscribe('nightingaleUserListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
