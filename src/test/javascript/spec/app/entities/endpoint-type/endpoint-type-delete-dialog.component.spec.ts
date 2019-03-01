/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessGuardV5TestModule } from '../../../test.module';
import { EndpointTypeDeleteDialogComponent } from 'app/entities/endpoint-type/endpoint-type-delete-dialog.component';
import { EndpointTypeService } from 'app/entities/endpoint-type/endpoint-type.service';

describe('Component Tests', () => {
    describe('EndpointType Management Delete Component', () => {
        let comp: EndpointTypeDeleteDialogComponent;
        let fixture: ComponentFixture<EndpointTypeDeleteDialogComponent>;
        let service: EndpointTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [EndpointTypeDeleteDialogComponent]
            })
                .overrideTemplate(EndpointTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EndpointTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EndpointTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
