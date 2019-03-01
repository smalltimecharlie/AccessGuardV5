/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessGuardV5TestModule } from '../../../test.module';
import { NightingaleUserComponent } from 'app/entities/nightingale-user/nightingale-user.component';
import { NightingaleUserService } from 'app/entities/nightingale-user/nightingale-user.service';
import { NightingaleUser } from 'app/shared/model/nightingale-user.model';

describe('Component Tests', () => {
    describe('NightingaleUser Management Component', () => {
        let comp: NightingaleUserComponent;
        let fixture: ComponentFixture<NightingaleUserComponent>;
        let service: NightingaleUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessGuardV5TestModule],
                declarations: [NightingaleUserComponent],
                providers: []
            })
                .overrideTemplate(NightingaleUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NightingaleUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NightingaleUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new NightingaleUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.nightingaleUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
