/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessGuardV5TestModule } from '../../../test.module';
import { ProductsUsersDeleteDialogComponent } from 'app/entities/products-users/products-users-delete-dialog.component';
import { ProductsUsersService } from 'app/entities/products-users/products-users.service';

describe('Component Tests', () => {
    describe('ProductsUsers Management Delete Component', () => {
        let comp: ProductsUsersDeleteDialogComponent;
        let fixture: ComponentFixture<ProductsUsersDeleteDialogComponent>;
        let service: ProductsUsersService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [ProductsUsersDeleteDialogComponent]
            })
                .overrideTemplate(ProductsUsersDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductsUsersDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductsUsersService);
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
