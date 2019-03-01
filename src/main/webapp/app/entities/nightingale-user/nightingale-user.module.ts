import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessGuardV5SharedModule } from 'app/shared';
import {
    NightingaleUserComponent,
    NightingaleUserDetailComponent,
    NightingaleUserUpdateComponent,
    NightingaleUserDeletePopupComponent,
    NightingaleUserDeleteDialogComponent,
    nightingaleUserRoute,
    nightingaleUserPopupRoute
} from './';

const ENTITY_STATES = [...nightingaleUserRoute, ...nightingaleUserPopupRoute];

@NgModule({
    imports: [AccessGuardV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NightingaleUserComponent,
        NightingaleUserDetailComponent,
        NightingaleUserUpdateComponent,
        NightingaleUserDeleteDialogComponent,
        NightingaleUserDeletePopupComponent
    ],
    entryComponents: [
        NightingaleUserComponent,
        NightingaleUserUpdateComponent,
        NightingaleUserDeleteDialogComponent,
        NightingaleUserDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessGuardV5NightingaleUserModule {}
