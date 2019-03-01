/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { AgreementTypeUpdateComponent } from 'app/entities/agreement-type/agreement-type-update.component';
import { AgreementTypeService } from 'app/entities/agreement-type/agreement-type.service';
import { AgreementType } from 'app/shared/model/agreement-type.model';

describe('Component Tests', () => {
    describe('AgreementType Management Update Component', () => {
        let comp: AgreementTypeUpdateComponent;
        let fixture: ComponentFixture<AgreementTypeUpdateComponent>;
        let service: AgreementTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [AgreementTypeUpdateComponent]
            })
                .overrideTemplate(AgreementTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AgreementTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgreementTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AgreementType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.agreementType = entity;
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
                    const entity = new AgreementType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.agreementType = entity;
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
