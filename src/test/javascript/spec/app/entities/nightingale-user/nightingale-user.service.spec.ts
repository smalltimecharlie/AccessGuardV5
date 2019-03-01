/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NightingaleUserService } from 'app/entities/nightingale-user/nightingale-user.service';
import { INightingaleUser, NightingaleUser } from 'app/shared/model/nightingale-user.model';

describe('Service Tests', () => {
    describe('NightingaleUser Service', () => {
        let injector: TestBed;
        let service: NightingaleUserService;
        let httpMock: HttpTestingController;
        let elemDefault: INightingaleUser;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(NightingaleUserService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new NightingaleUser(0, 'AAAAAAA', false, 'AAAAAAA', 'image/png', 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        cognitoSignupDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a NightingaleUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        cognitoSignupDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        cognitoSignupDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new NightingaleUser(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a NightingaleUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        email: 'BBBBBB',
                        active: true,
                        cognitoUsername: 'BBBBBB',
                        cognitoEvent: 'BBBBBB',
                        cognitoSignupDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        cognitoSignupDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of NightingaleUser', async () => {
                const returnedFromService = Object.assign(
                    {
                        email: 'BBBBBB',
                        active: true,
                        cognitoUsername: 'BBBBBB',
                        cognitoEvent: 'BBBBBB',
                        cognitoSignupDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        cognitoSignupDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a NightingaleUser', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
