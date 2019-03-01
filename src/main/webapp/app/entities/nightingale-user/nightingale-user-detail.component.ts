import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { INightingaleUser } from 'app/shared/model/nightingale-user.model';

@Component({
    selector: 'jhi-nightingale-user-detail',
    templateUrl: './nightingale-user-detail.component.html'
})
export class NightingaleUserDetailComponent implements OnInit {
    nightingaleUser: INightingaleUser;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nightingaleUser }) => {
            this.nightingaleUser = nightingaleUser;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
