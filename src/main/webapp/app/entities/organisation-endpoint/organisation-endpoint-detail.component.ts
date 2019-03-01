import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';

@Component({
    selector: 'jhi-organisation-endpoint-detail',
    templateUrl: './organisation-endpoint-detail.component.html'
})
export class OrganisationEndpointDetailComponent implements OnInit {
    organisationEndpoint: IOrganisationEndpoint;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ organisationEndpoint }) => {
            this.organisationEndpoint = organisationEndpoint;
        });
    }

    previousState() {
        window.history.back();
    }
}
