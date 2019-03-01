/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { AgreementTypeDetailComponent } from 'app/entities/agreement-type/agreement-type-detail.component';
import { AgreementType } from 'app/shared/model/agreement-type.model';

describe('Component Tests', () => {
    describe('AgreementType Management Detail Component', () => {
        let comp: AgreementTypeDetailComponent;
        let fixture: ComponentFixture<AgreementTypeDetailComponent>;
        const route = ({ data: of({ agreementType: new AgreementType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [AgreementTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AgreementTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AgreementTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.agreementType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
