import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';

@Component({
    selector: 'jhi-location-type-update',
    templateUrl: './location-type-update.component.html'
})
export class LocationTypeUpdateComponent implements OnInit {
    locationType: ILocationType;
    isSaving: boolean;

    constructor(protected locationTypeService: LocationTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locationType }) => {
            this.locationType = locationType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.locationType.id !== undefined) {
            this.subscribeToSaveResponse(this.locationTypeService.update(this.locationType));
        } else {
            this.subscribeToSaveResponse(this.locationTypeService.create(this.locationType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationType>>) {
        result.subscribe((res: HttpResponse<ILocationType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
