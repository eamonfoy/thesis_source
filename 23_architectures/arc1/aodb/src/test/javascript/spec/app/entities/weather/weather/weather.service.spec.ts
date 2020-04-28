import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { WeatherService } from 'app/entities/weather/weather/weather.service';
import { IWeather, Weather } from 'app/shared/model/weather/weather.model';

describe('Service Tests', () => {
  describe('Weather Service', () => {
    let injector: TestBed;
    let service: WeatherService;
    let httpMock: HttpTestingController;
    let elemDefault: IWeather;
    let expectedResult: IWeather | IWeather[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WeatherService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Weather(0, 'AAAAAAA', currentDate, 'AAAAAAA', 0, 0, 0, 0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            forecastDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Weather', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            forecastDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            forecastDate: currentDate
          },
          returnedFromService
        );

        service.create(new Weather()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Weather', () => {
        const returnedFromService = Object.assign(
          {
            airportCode: 'BBBBBB',
            forecastDate: currentDate.format(DATE_TIME_FORMAT),
            dayName: 'BBBBBB',
            highTemperatureValue: 1,
            lowTemperatureValue: 1,
            feelsLikeHighTemperature: 1,
            feelsLikeLowTemperature: 1,
            phrase: 'BBBBBB',
            probabilityOfPrecip: 1,
            probabilityOfPrecipUnits: 'BBBBBB',
            nightPhrase: 'BBBBBB',
            nightIcon: 1,
            nightProbabilityOfPrecip: 1,
            nightProbabilityOfPrecipUnits: 'BBBBBB',
            icon: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            forecastDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Weather', () => {
        const returnedFromService = Object.assign(
          {
            airportCode: 'BBBBBB',
            forecastDate: currentDate.format(DATE_TIME_FORMAT),
            dayName: 'BBBBBB',
            highTemperatureValue: 1,
            lowTemperatureValue: 1,
            feelsLikeHighTemperature: 1,
            feelsLikeLowTemperature: 1,
            phrase: 'BBBBBB',
            probabilityOfPrecip: 1,
            probabilityOfPrecipUnits: 'BBBBBB',
            nightPhrase: 'BBBBBB',
            nightIcon: 1,
            nightProbabilityOfPrecip: 1,
            nightProbabilityOfPrecipUnits: 'BBBBBB',
            icon: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            forecastDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Weather', () => {
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
