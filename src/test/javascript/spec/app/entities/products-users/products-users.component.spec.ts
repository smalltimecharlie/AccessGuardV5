/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessGuardV5TestModule } from '../../../test.module';
import { ProductsUsersComponent } from 'app/entities/products-users/products-users.component';
import { ProductsUsersService } from 'app/entities/products-users/products-users.service';
import { ProductsUsers } from 'app/shared/model/products-users.model';

describe('Component Tests', () => {
    describe('ProductsUsers Management Component', () => {
        let comp: ProductsUsersComponent;
        let fixture: ComponentFixture<ProductsUsersComponent>;
        let service: ProductsUsersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [ProductsUsersComponent],
                providers: []
            })
                .overrideTemplate(ProductsUsersComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductsUsersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductsUsersService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductsUsers(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productsUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
