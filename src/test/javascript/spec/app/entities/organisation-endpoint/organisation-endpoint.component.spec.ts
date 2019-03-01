/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessGuardV5TestModule } from '../../../test.module';
import { OrganisationEndpointComponent } from 'app/entities/organisation-endpoint/organisation-endpoint.component';
import { OrganisationEndpointService } from 'app/entities/organisation-endpoint/organisation-endpoint.service';
import { OrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';

describe('Component Tests', () => {
    describe('OrganisationEndpoint Management Component', () => {
        let comp: OrganisationEndpointComponent;
        let fixture: ComponentFixture<OrganisationEndpointComponent>;
        let service: OrganisationEndpointService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [OrganisationEndpointComponent],
                providers: []
            })
                .overrideTemplate(OrganisationEndpointComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrganisationEndpointComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganisationEndpointService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OrganisationEndpoint(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.organisationEndpoints[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
