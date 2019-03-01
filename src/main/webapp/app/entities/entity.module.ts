import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'agreement-type',
                loadChildren: './agreement-type/agreement-type.module#AccessGuardV5AgreementTypeModule'
            },
            {
                path: 'agreement',
                loadChildren: './agreement/agreement.module#AccessGuardV5AgreementModule'
            },
            {
                path: 'nightingale-user',
                loadChildren: './nightingale-user/nightingale-user.module#AccessGuardV5NightingaleUserModule'
            },
            {
                path: 'agreement-organisation',
                loadChildren: './agreement-organisation/agreement-organisation.module#AccessGuardV5AgreementOrganisationModule'
            },
            {
                path: 'organisation-endpoint',
                loadChildren: './organisation-endpoint/organisation-endpoint.module#AccessGuardV5OrganisationEndpointModule'
            },
            {
                path: 'products',
                loadChildren: './products/products.module#AccessGuardV5ProductsModule'
            },
            {
                path: 'products-users',
                loadChildren: './products-users/products-users.module#AccessGuardV5ProductsUsersModule'
            },
            {
                path: 'location-type',
                loadChildren: './location-type/location-type.module#AccessGuardV5LocationTypeModule'
            },
            {
                path: 'endpoint-type',
                loadChildren: './endpoint-type/endpoint-type.module#AccessGuardV5EndpointTypeModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessGuardV5EntityModule {}
