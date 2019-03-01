/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { OrganisationEndpointDetailComponent } from 'app/entities/organisation-endpoint/organisation-endpoint-detail.component';
import { OrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';

describe('Component Tests', () => {
    describe('OrganisationEndpoint Management Detail Component', () => {
        let comp: OrganisationEndpointDetailComponent;
        let fixture: ComponentFixture<OrganisationEndpointDetailComponent>;
        const route = ({ data: of({ organisationEndpoint: new OrganisationEndpoint(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [OrganisationEndpointDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrganisationEndpointDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrganisationEndpointDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.organisationEndpoint).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
