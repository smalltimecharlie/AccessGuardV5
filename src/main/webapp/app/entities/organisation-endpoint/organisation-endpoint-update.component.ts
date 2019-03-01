import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';
import { OrganisationEndpointService } from './organisation-endpoint.service';
import { ILocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from 'app/entities/location-type';
import { IEndpointType } from 'app/shared/model/endpoint-type.model';
import { EndpointTypeService } from 'app/entities/endpoint-type';
import { IAgreementOrganisation } from 'app/shared/model/agreement-organisation.model';
import { AgreementOrganisationService } from 'app/entities/agreement-organisation';

@Component({
    selector: 'jhi-organisation-endpoint-update',
    templateUrl: './organisation-endpoint-update.component.html'
})
export class OrganisationEndpointUpdateComponent implements OnInit {
    organisationEndpoint: IOrganisationEndpoint;
    isSaving: boolean;

    locationtypes: ILocationType[];

    endpointtypes: IEndpointType[];

    agreementorganisations: IAgreementOrganisation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected organisationEndpointService: OrganisationEndpointService,
        protected locationTypeService: LocationTypeService,
        protected endpointTypeService: EndpointTypeService,
        protected agreementOrganisationService: AgreementOrganisationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ organisationEndpoint }) => {
            this.organisationEndpoint = organisationEndpoint;
        });
        this.locationTypeService
            .query({ filter: 'organisationendpoint-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILocationType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILocationType[]>) => response.body)
            )
            .subscribe(
                (res: ILocationType[]) => {
                    if (!this.organisationEndpoint.locationType || !this.organisationEndpoint.locationType.id) {
                        this.locationtypes = res;
                    } else {
                        this.locationTypeService
                            .find(this.organisationEndpoint.locationType.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ILocationType>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ILocationType>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ILocationType) => (this.locationtypes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.endpointTypeService
            .query({ filter: 'organisationendpoint-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IEndpointType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEndpointType[]>) => response.body)
            )
            .subscribe(
                (res: IEndpointType[]) => {
                    if (!this.organisationEndpoint.endpointType || !this.organisationEndpoint.endpointType.id) {
                        this.endpointtypes = res;
                    } else {
                        this.endpointTypeService
                            .find(this.organisationEndpoint.endpointType.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IEndpointType>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IEndpointType>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IEndpointType) => (this.endpointtypes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.organisationEndpoint.id !== undefined) {
            this.subscribeToSaveResponse(this.organisationEndpointService.update(this.organisationEndpoint));
        } else {
            this.subscribeToSaveResponse(this.organisationEndpointService.create(this.organisationEndpoint));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisationEndpoint>>) {
        result.subscribe(
            (res: HttpResponse<IOrganisationEndpoint>) => this.onSaveSuccess(),
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

    trackLocationTypeById(index: number, item: ILocationType) {
        return item.id;
    }

    trackEndpointTypeById(index: number, item: IEndpointType) {
        return item.id;
    }

    trackAgreementOrganisationById(index: number, item: IAgreementOrganisation) {
        return item.id;
    }
}
