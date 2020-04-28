import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeather } from 'app/shared/model/weather/weather.model';

type EntityResponseType = HttpResponse<IWeather>;
type EntityArrayResponseType = HttpResponse<IWeather[]>;

@Injectable({ providedIn: 'root' })
export class WeatherService {
  public resourceUrl = SERVER_API_URL + 'services/weather/api/weathers';

  constructor(protected http: HttpClient) {}

  create(weather: IWeather): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weather);
    return this.http
      .post<IWeather>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(weather: IWeather): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weather);
    return this.http
      .put<IWeather>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWeather>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWeather[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(weather: IWeather): IWeather {
    const copy: IWeather = Object.assign({}, weather, {
      forecastDate: weather.forecastDate && weather.forecastDate.isValid() ? weather.forecastDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.forecastDate = res.body.forecastDate ? moment(res.body.forecastDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((weather: IWeather) => {
        weather.forecastDate = weather.forecastDate ? moment(weather.forecastDate) : undefined;
      });
    }
    return res;
  }
}
