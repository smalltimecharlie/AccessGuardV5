import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAgreementType } from 'app/shared/model/agreement-type.model';
import { AgreementTypeService } from './agreement-type.service';

@Component({
    selector: 'jhi-agreement-type-update',
    templateUrl: './agreement-type-update.component.html'
})
export class AgreementTypeUpdateComponent implements OnInit {
    agreementType: IAgreementType;
    isSaving: boolean;
    agreementTypeStartDate: string;
    agreementTypeEndDate: string;

    constructor(protected agreementTypeService: AgreementTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agreementType }) => {
            this.agreementType = agreementType;
            this.agreementTypeStartDate =
                this.agreementType.agreementTypeStartDate != null
                    ? this.agreementType.agreementTypeStartDate.format(DATE_TIME_FORMAT)
                    : null;
            this.agreementTypeEndDate =
                this.agreementType.agreementTypeEndDate != null ? this.agreementType.agreementTypeEndDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.agreementType.agreementTypeStartDate =
            this.agreementTypeStartDate != null ? moment(this.agreementTypeStartDate, DATE_TIME_FORMAT) : null;
        this.agreementType.agreementTypeEndDate =
            this.agreementTypeEndDate != null ? moment(this.agreementTypeEndDate, DATE_TIME_FORMAT) : null;
        if (this.agreementType.id !== undefined) {
            this.subscribeToSaveResponse(this.agreementTypeService.update(this.agreementType));
        } else {
            this.subscribeToSaveResponse(this.agreementTypeService.create(this.agreementType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgreementType>>) {
        result.subscribe((res: HttpResponse<IAgreementType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
