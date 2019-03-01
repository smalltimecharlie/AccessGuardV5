/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { ProductsUsersUpdateComponent } from 'app/entities/products-users/products-users-update.component';
import { ProductsUsersService } from 'app/entities/products-users/products-users.service';
import { ProductsUsers } from 'app/shared/model/products-users.model';

describe('Component Tests', () => {
    describe('ProductsUsers Management Update Component', () => {
        let comp: ProductsUsersUpdateComponent;
        let fixture: ComponentFixture<ProductsUsersUpdateComponent>;
        let service: ProductsUsersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [ProductsUsersUpdateComponent]
            })
                .overrideTemplate(ProductsUsersUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductsUsersUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductsUsersService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductsUsers(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productsUsers = entity;
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
                    const entity = new ProductsUsers();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productsUsers = entity;
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
