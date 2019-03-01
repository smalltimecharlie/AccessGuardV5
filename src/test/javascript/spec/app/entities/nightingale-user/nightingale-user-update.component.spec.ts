/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessGuardV5TestModule } from '../../../test.module';
import { NightingaleUserUpdateComponent } from 'app/entities/nightingale-user/nightingale-user-update.component';
import { NightingaleUserService } from 'app/entities/nightingale-user/nightingale-user.service';
import { NightingaleUser } from 'app/shared/model/nightingale-user.model';

describe('Component Tests', () => {
    describe('NightingaleUser Management Update Component', () => {
        let comp: NightingaleUserUpdateComponent;
        let fixture: ComponentFixture<NightingaleUserUpdateComponent>;
        let service: NightingaleUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [NightingaleUserUpdateComponent]
            })
                .overrideTemplate(NightingaleUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NightingaleUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NightingaleUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new NightingaleUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.nightingaleUser = entity;
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
                    const entity = new NightingaleUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.nightingaleUser = entity;
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
