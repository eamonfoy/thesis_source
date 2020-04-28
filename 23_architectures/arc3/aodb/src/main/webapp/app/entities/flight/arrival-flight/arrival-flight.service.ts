import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

type EntityResponseType = HttpResponse<IArrivalFlight>;
type EntityArrayResponseType = HttpResponse<IArrivalFlight[]>;

@Injectable({ providedIn: 'root' })
export class ArrivalFlightService {
  public resourceUrl = SERVER_API_URL + 'services/flight/api/arrival-flights';

  constructor(protected http: HttpClient) {}

  create(arrivalFlight: IArrivalFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivalFlight);
    return this.http
      .post<IArrivalFlight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(arrivalFlight: IArrivalFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivalFlight);
    return this.http
      .put<IArrivalFlight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArrivalFlight>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArrivalFlight[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(arrivalFlight: IArrivalFlight): IArrivalFlight {
    const copy: IArrivalFlight = Object.assign({}, arrivalFlight, {
      actual: arrivalFlight.actual && arrivalFlight.actual.isValid() ? arrivalFlight.actual.toJSON() : undefined,
      estimated: arrivalFlight.estimated && arrivalFlight.estimated.isValid() ? arrivalFlight.estimated.toJSON() : undefined,
      scheduled: arrivalFlight.scheduled && arrivalFlight.scheduled.isValid() ? arrivalFlight.scheduled.toJSON() : undefined
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
      res.body.forEach((arrivalFlight: IArrivalFlight) => {
        arrivalFlight.actual = arrivalFlight.actual ? moment(arrivalFlight.actual) : undefined;
        arrivalFlight.estimated = arrivalFlight.estimated ? moment(arrivalFlight.estimated) : undefined;
        arrivalFlight.scheduled = arrivalFlight.scheduled ? moment(arrivalFlight.scheduled) : undefined;
      });
    }
    return res;
  }
}
