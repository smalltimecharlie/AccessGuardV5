import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAgreementOrganisation } from 'app/shared/model/agreement-organisation.model';
import { AgreementOrganisationService } from './agreement-organisation.service';
import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from 'app/entities/agreement';

@Component({
    selector: 'jhi-agreement-organisation-update',
    templateUrl: './agreement-organisation-update.component.html'
})
export class AgreementOrganisationUpdateComponent implements OnInit {
    agreementOrganisation: IAgreementOrganisation;
    isSaving: boolean;

    agreements: IAgreement[];
    createdDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected agreementOrganisationService: AgreementOrganisationService,
        protected agreementService: AgreementService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agreementOrganisation }) => {
            this.agreementOrganisation = agreementOrganisation;
            this.createdDate =
                this.agreementOrganisation.createdDate != null ? this.agreementOrganisation.createdDate.format(DATE_TIME_FORMAT) : null;
        });
        this.agreementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAgreement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAgreement[]>) => response.body)
            )
            .subscribe((res: IAgreement[]) => (this.agreements = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.agreementOrganisation.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        if (this.agreementOrganisation.id !== undefined) {
            this.subscribeToSaveResponse(this.agreementOrganisationService.update(this.agreementOrganisation));
        } else {
            this.subscribeToSaveResponse(this.agreementOrganisationService.create(this.agreementOrganisation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgreementOrganisation>>) {
        result.subscribe(
            (res: HttpResponse<IAgreementOrganisation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAgreementById(index: number, item: IAgreement) {
        return item.id;
    }
}
