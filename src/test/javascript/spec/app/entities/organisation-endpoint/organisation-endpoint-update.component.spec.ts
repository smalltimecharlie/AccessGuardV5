/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { OrganisationEndpointUpdateComponent } from 'app/entities/organisation-endpoint/organisation-endpoint-update.component';
import { OrganisationEndpointService } from 'app/entities/organisation-endpoint/organisation-endpoint.service';
import { OrganisationEndpoint } from 'app/shared/model/organisation-endpoint.model';

describe('Component Tests', () => {
    describe('OrganisationEndpoint Management Update Component', () => {
        let comp: OrganisationEndpointUpdateComponent;
        let fixture: ComponentFixture<OrganisationEndpointUpdateComponent>;
        let service: OrganisationEndpointService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [OrganisationEndpointUpdateComponent]
            })
                .overrideTemplate(OrganisationEndpointUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrganisationEndpointUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganisationEndpointService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrganisationEndpoint(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.organisationEndpoint = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrganisationEndpoint();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.organisationEndpoint = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
