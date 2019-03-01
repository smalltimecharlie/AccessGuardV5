import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { INightingaleUser } from 'app/shared/model/nightingale-user.model';
import { NightingaleUserService } from './nightingale-user.service';
import { IAgreementOrganisation } from 'app/shared/model/agreement-organisation.model';
import { AgreementOrganisationService } from 'app/entities/agreement-organisation';

@Component({
    selector: 'jhi-nightingale-user-update',
    templateUrl: './nightingale-user-update.component.html'
})
export class NightingaleUserUpdateComponent implements OnInit {
    nightingaleUser: INightingaleUser;
    isSaving: boolean;

    agreementorganisations: IAgreementOrganisation[];
    cognitoSignupDate: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected nightingaleUserService: NightingaleUserService,
        protected agreementOrganisationService: AgreementOrganisationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nightingaleUser }) => {
            this.nightingaleUser = nightingaleUser;
            this.cognitoSignupDate =
                this.nightingaleUser.cognitoSignupDate != null ? this.nightingaleUser.cognitoSignupDate.format(DATE_TIME_FORMAT) : null;
        });
        this.agreementOrganisationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAgreementOrganisation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAgreementOrganisation[]>) => response.body)
            )
            .subscribe(
                (res: IAgreementOrganisation[]) => (this.agreementorganisations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.nightingaleUser.cognitoSignupDate = this.cognitoSignupDate != null ? moment(this.cognitoSignupDate, DATE_TIME_FORMAT) : null;
        if (this.nightingaleUser.id !== undefined) {
            this.subscribeToSaveResponse(this.nightingaleUserService.update(this.nightingaleUser));
        } else {
            this.subscribeToSaveResponse(this.nightingaleUserService.create(this.nightingaleUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INightingaleUser>>) {
        result.subscribe((res: HttpResponse<INightingaleUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAgreementOrganisationById(index: number, item: IAgreementOrganisation) {
        return item.id;
    }
}
