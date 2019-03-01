/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { NightingaleUserDetailComponent } from 'app/entities/nightingale-user/nightingale-user-detail.component';
import { NightingaleUser } from 'app/shared/model/nightingale-user.model';

describe('Component Tests', () => {
    describe('NightingaleUser Management Detail Component', () => {
        let comp: NightingaleUserDetailComponent;
        let fixture: ComponentFixture<NightingaleUserDetailComponent>;
        const route = ({ data: of({ nightingaleUser: new NightingaleUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [NightingaleUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NightingaleUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NightingaleUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.nightingaleUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
