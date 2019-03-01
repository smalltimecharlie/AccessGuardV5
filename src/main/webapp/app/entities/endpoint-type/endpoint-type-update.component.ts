import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEndpointType } from 'app/shared/model/endpoint-type.model';
import { EndpointTypeService } from './endpoint-type.service';

@Component({
    selector: 'jhi-endpoint-type-update',
    templateUrl: './endpoint-type-update.component.html'
})
export class EndpointTypeUpdateComponent implements OnInit {
    endpointType: IEndpointType;
    isSaving: boolean;

    constructor(protected endpointTypeService: EndpointTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ endpointType }) => {
            this.endpointType = endpointType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.endpointType.id !== undefined) {
            this.subscribeToSaveResponse(this.endpointTypeService.update(this.endpointType));
        } else {
            this.subscribeToSaveResponse(this.endpointTypeService.create(this.endpointType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndpointType>>) {
        result.subscribe((res: HttpResponse<IEndpointType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
