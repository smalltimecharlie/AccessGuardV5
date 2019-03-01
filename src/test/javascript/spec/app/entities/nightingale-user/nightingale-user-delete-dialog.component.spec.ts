/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessGuardV5TestModule } from '../../../test.module';
import { NightingaleUserDeleteDialogComponent } from 'app/entities/nightingale-user/nightingale-user-delete-dialog.component';
import { NightingaleUserService } from 'app/entities/nightingale-user/nightingale-user.service';

describe('Component Tests', () => {
    describe('NightingaleUser Management Delete Component', () => {
        let comp: NightingaleUserDeleteDialogComponent;
        let fixture: ComponentFixture<NightingaleUserDeleteDialogComponent>;
        let service: NightingaleUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [NightingaleUserDeleteDialogComponent]
            })
                .overrideTemplate(NightingaleUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NightingaleUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NightingaleUserService);
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
