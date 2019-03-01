/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { ProductsUsersDetailComponent } from 'app/entities/products-users/products-users-detail.component';
import { ProductsUsers } from 'app/shared/model/products-users.model';

describe('Component Tests', () => {
    describe('ProductsUsers Management Detail Component', () => {
        let comp: ProductsUsersDetailComponent;
        let fixture: ComponentFixture<ProductsUsersDetailComponent>;
        const route = ({ data: of({ productsUsers: new ProductsUsers(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [ProductsUsersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductsUsersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductsUsersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productsUsers).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
