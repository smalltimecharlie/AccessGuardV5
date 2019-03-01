import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessGuardV5SharedModule } from 'app/shared';
import {
    OrganisationEndpointComponent,
    OrganisationEndpointDetailComponent,
    OrganisationEndpointUpdateComponent,
    OrganisationEndpointDeletePopupComponent,
    OrganisationEndpointDeleteDialogComponent,
    organisationEndpointRoute,
    organisationEndpointPopupRoute
} from './';

const ENTITY_STATES = [...organisationEndpointRoute, ...organisationEndpointPopupRoute];

@NgModule({
    imports: [AccessGuardV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrganisationEndpointComponent,
        OrganisationEndpointDetailComponent,
        OrganisationEndpointUpdateComponent,
        OrganisationEndpointDeleteDialogComponent,
        OrganisationEndpointDeletePopupComponent
    ],
    entryComponents: [
        OrganisationEndpointComponent,
        OrganisationEndpointUpdateComponent,
        OrganisationEndpointDeleteDialogComponent,
        OrganisationEndpointDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessGuardV5OrganisationEndpointModule {}
