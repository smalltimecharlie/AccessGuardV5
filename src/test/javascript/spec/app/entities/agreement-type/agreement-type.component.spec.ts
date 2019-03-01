/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessGuardV5TestModule } from '../../../test.module';
import { AgreementTypeComponent } from 'app/entities/agreement-type/agreement-type.component';
import { AgreementTypeService } from 'app/entities/agreement-type/agreement-type.service';
import { AgreementType } from 'app/shared/model/agreement-type.model';

describe('Component Tests', () => {
    describe('AgreementType Management Component', () => {
        let comp: AgreementTypeComponent;
        let fixture: ComponentFixture<AgreementTypeComponent>;
        let service: AgreementTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [AgreementTypeComponent],
                providers: []
            })
                .overrideTemplate(AgreementTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AgreementTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgreementTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AgreementType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.agreementTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
