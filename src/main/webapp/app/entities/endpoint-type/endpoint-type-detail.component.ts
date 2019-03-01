import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndpointType } from 'app/shared/model/endpoint-type.model';

@Component({
    selector: 'jhi-endpoint-type-detail',
    templateUrl: './endpoint-type-detail.component.html'
})
export class EndpointTypeDetailComponent implements OnInit {
    endpointType: IEndpointType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ endpointType }) => {
            this.endpointType = endpointType;
        });
    }

    previousState() {
        window.history.back();
    }
}
