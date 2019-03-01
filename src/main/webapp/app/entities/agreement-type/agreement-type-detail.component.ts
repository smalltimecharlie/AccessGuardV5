import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgreementType } from 'app/shared/model/agreement-type.model';

@Component({
    selector: 'jhi-agreement-type-detail',
    templateUrl: './agreement-type-detail.component.html'
})
export class AgreementTypeDetailComponent implements OnInit {
    agreementType: IAgreementType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ agreementType }) => {
            this.agreementType = agreementType;
        });
    }

    previousState() {
        window.history.back();
    }
}
