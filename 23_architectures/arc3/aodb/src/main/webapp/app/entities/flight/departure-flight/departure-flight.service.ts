import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartureFlight } from 'app/shared/model/flight/departure-flight.model';

type EntityResponseType = HttpResponse<IDepartureFlight>;
type EntityArrayResponseType = HttpResponse<IDepartureFlight[]>;

@Injectable({ providedIn: 'root' })
export class DepartureFlightService {
  public resourceUrl = SERVER_API_URL + 'services/flight/api/departure-flights';

  constructor(protected http: HttpClient) {}

  create(departureFlight: IDepartureFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(departureFlight);
    return this.http
      .post<IDepartureFlight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(departureFlight: IDepartureFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(departureFlight);
    return this.http
      .put<IDepartureFlight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDepartureFlight>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDepartureFlight[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(departureFlight: IDepartureFlight): IDepartureFlight {
    const copy: IDepartureFlight = Object.assign({}, departureFlight, {
      actual: departureFlight.actual && departureFlight.actual.isValid() ? departureFlight.actual.toJSON() : undefined,
      estimated: departureFlight.estimated && departureFlight.estimated.isValid() ? departureFlight.estimated.toJSON() : undefined,
      scheduled: departureFlight.scheduled && departureFlight.scheduled.isValid() ? departureFlight.scheduled.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.actual = res.body.actual ? moment(res.body.actual) : undefined;
      res.body.estimated = res.body.estimated ? moment(res.body.estimated) : undefined;
      res.body.scheduled = res.body.scheduled ? moment(res.body.scheduled) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((departureFlight: IDepartureFlight) => {
        departureFlight.actual = departureFlight.actual ? moment(departureFlight.actual) : undefined;
        departureFlight.estimated = departureFlight.estimated ? moment(departureFlight.estimated) : undefined;
        departureFlight.scheduled = departureFlight.scheduled ? moment(departureFlight.scheduled) : undefined;
      });
    }
    return res;
  }
}
