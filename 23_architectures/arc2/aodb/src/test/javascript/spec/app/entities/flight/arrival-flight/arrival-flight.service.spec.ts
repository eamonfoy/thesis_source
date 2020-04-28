import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ArrivalFlightService } from 'app/entities/flight/arrival-flight/arrival-flight.service';
import { IArrivalFlight, ArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

describe('Service Tests', () => {
  describe('ArrivalFlight Service', () => {
    let injector: TestBed;
    let service: ArrivalFlightService;
    let httpMock: HttpTestingController;
    let elemDefault: IArrivalFlight;
    let expectedResult: IArrivalFlight | IArrivalFlight[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ArrivalFlightService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ArrivalFlight(
        0,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            actual: currentDate.format(DATE_TIME_FORMAT),
            estimated: currentDate.format(DATE_TIME_FORMAT),
            scheduled: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ArrivalFlight', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            actual: currentDate.format(DATE_TIME_FORMAT),
            estimated: currentDate.format(DATE_TIME_FORMAT),
            scheduled: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            actual: currentDate,
            estimated: currentDate,
            scheduled: currentDate
          },
          returnedFromService
        );

        service.create(new ArrivalFlight()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ArrivalFlight', () => {
        const returnedFromService = Object.assign(
          {
            actual: currentDate.format(DATE_TIME_FORMAT),
            estimated: currentDate.format(DATE_TIME_FORMAT),
            scheduled: currentDate.format(DATE_TIME_FORMAT),
            city: 'BBBBBB',
            aircraft: 'BBBBBB',
            terminal: 'BBBBBB',
            duration: 'BBBBBB',
            tailNumber: 'BBBBBB',
            airportCode: 'BBBBBB',
            airline: 'BBBBBB',
            flightNumber: 'BBBBBB',
            claim: 'BBBBBB',
            status: 'BBBBBB',
            statusText: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            actual: currentDate,
            estimated: currentDate,
            scheduled: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ArrivalFlight', () => {
        const returnedFromService = Object.assign(
          {
            actual: currentDate.format(DATE_TIME_FORMAT),
            estimated: currentDate.format(DATE_TIME_FORMAT),
            scheduled: currentDate.format(DATE_TIME_FORMAT),
            city: 'BBBBBB',
            aircraft: 'BBBBBB',
            terminal: 'BBBBBB',
            duration: 'BBBBBB',
            tailNumber: 'BBBBBB',
            airportCode: 'BBBBBB',
            airline: 'BBBBBB',
            flightNumber: 'BBBBBB',
            claim: 'BBBBBB',
            status: 'BBBBBB',
            statusText: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            actual: currentDate,
            estimated: currentDate,
            scheduled: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ArrivalFlight', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
