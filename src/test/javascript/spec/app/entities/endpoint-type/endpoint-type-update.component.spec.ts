/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { EndpointTypeUpdateComponent } from 'app/entities/endpoint-type/endpoint-type-update.component';
import { EndpointTypeService } from 'app/entities/endpoint-type/endpoint-type.service';
import { EndpointType } from 'app/shared/model/endpoint-type.model';

describe('Component Tests', () => {
    describe('EndpointType Management Update Component', () => {
        let comp: EndpointTypeUpdateComponent;
        let fixture: ComponentFixture<EndpointTypeUpdateComponent>;
        let service: EndpointTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [EndpointTypeUpdateComponent]
            })
                .overrideTemplate(EndpointTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EndpointTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EndpointTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EndpointType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.endpointType = entity;
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
                    const entity = new EndpointType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.endpointType = entity;
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
