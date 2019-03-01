/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessGuardV5TestModule } from '../../../test.module';
import { OrganisationEndpointDeleteDialogComponent } from 'app/entities/organisation-endpoint/organisation-endpoint-delete-dialog.component';
import { OrganisationEndpointService } from 'app/entities/organisation-endpoint/organisation-endpoint.service';

describe('Component Tests', () => {
    describe('OrganisationEndpoint Management Delete Component', () => {
        let comp: OrganisationEndpointDeleteDialogComponent;
        let fixture: ComponentFixture<OrganisationEndpointDeleteDialogComponent>;
        let service: OrganisationEndpointService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [OrganisationEndpointDeleteDialogComponent]
            })
                .overrideTemplate(OrganisationEndpointDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrganisationEndpointDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganisationEndpointService);
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
