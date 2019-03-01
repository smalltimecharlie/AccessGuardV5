import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';
import { IAgreementType } from 'app/shared/model/agreement-type.model';
import { AgreementTypeService } from 'app/entities/agreement-type';

@Component({
    selector: 'jhi-agreement-update',
    templateUrl: './agreement-update.component.html'
})
export class AgreementUpdateComponent implements OnInit {
    agreement: IAgreement;
    isSaving: boolean;

    agreementtypes: IAgreementType[];
    agreementStartDate: string;
    agreementEndDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected agreementService: AgreementService,
        protected agreementTypeService: AgreementTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agreement }) => {
            this.agreement = agreement;
            this.agreementStartDate =
                this.agreement.agreementStartDate != null ? this.agreement.agreementStartDate.format(DATE_TIME_FORMAT) : null;
            this.agreementEndDate =
                this.agreement.agreementEndDate != null ? this.agreement.agreementEndDate.format(DATE_TIME_FORMAT) : null;
        });
        this.agreementTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAgreementType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAgreementType[]>) => response.body)
            )
            .subscribe((res: IAgreementType[]) => (this.agreementtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.agreement.agreementStartDate = this.agreementStartDate != null ? moment(this.agreementStartDate, DATE_TIME_FORMAT) : null;
        this.agreement.agreementEndDate = this.agreementEndDate != null ? moment(this.agreementEndDate, DATE_TIME_FORMAT) : null;
        if (this.agreement.id !== undefined) {
            this.subscribeToSaveResponse(this.agreementService.update(this.agreement));
        } else {
            this.subscribeToSaveResponse(this.agreementService.create(this.agreement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgreement>>) {
        result.subscribe((res: HttpResponse<IAgreement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAgreementTypeById(index: number, item: IAgreementType) {
        return item.id;
    }
}
