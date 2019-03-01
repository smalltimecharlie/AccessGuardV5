/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessGuardV5TestModule } from '../../../test.module';
import { EndpointTypeComponent } from 'app/entities/endpoint-type/endpoint-type.component';
import { EndpointTypeService } from 'app/entities/endpoint-type/endpoint-type.service';
import { EndpointType } from 'app/shared/model/endpoint-type.model';

describe('Component Tests', () => {
    describe('EndpointType Management Component', () => {
        let comp: EndpointTypeComponent;
        let fixture: ComponentFixture<EndpointTypeComponent>;
        let service: EndpointTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [EndpointTypeComponent],
                providers: []
            })
                .overrideTemplate(EndpointTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EndpointTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EndpointTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EndpointType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.endpointTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
