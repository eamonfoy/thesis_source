import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DepartureFlightService } from 'app/entities/flight/departure-flight/departure-flight.service';
import { IDepartureFlight, DepartureFlight } from 'app/shared/model/flight/departure-flight.model';

describe('Service Tests', () => {
  describe('DepartureFlight Service', () => {
    let injector: TestBed;
    let service: DepartureFlightService;
    let httpMock: HttpTestingController;
    let elemDefault: IDepartureFlight;
    let expectedResult: IDepartureFlight | IDepartureFlight[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DepartureFlightService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DepartureFlight(
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

      it('should create a DepartureFlight', () => {
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

        service.create(new DepartureFlight()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DepartureFlight', () => {
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
            gate: 'BBBBBB',
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

      it('should return a list of DepartureFlight', () => {
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
            gate: 'BBBBBB',
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

      it('should delete a DepartureFlight', () => {
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
