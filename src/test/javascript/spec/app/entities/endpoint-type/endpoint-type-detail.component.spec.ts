/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { EndpointTypeDetailComponent } from 'app/entities/endpoint-type/endpoint-type-detail.component';
import { EndpointType } from 'app/shared/model/endpoint-type.model';

describe('Component Tests', () => {
    describe('EndpointType Management Detail Component', () => {
        let comp: EndpointTypeDetailComponent;
        let fixture: ComponentFixture<EndpointTypeDetailComponent>;
        const route = ({ data: of({ endpointType: new EndpointType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [EndpointTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EndpointTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EndpointTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.endpointType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
