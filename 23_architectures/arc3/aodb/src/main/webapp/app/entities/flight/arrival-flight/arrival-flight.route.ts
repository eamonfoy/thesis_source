import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArrivalFlight, ArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';
import { ArrivalFlightService } from './arrival-flight.service';
import { ArrivalFlightComponent } from './arrival-flight.component';
import { ArrivalFlightDetailComponent } from './arrival-flight-detail.component';
import { ArrivalFlightUpdateComponent } from './arrival-flight-update.component';

@Injectable({ providedIn: 'root' })
export class ArrivalFlightResolve implements Resolve<IArrivalFlight> {
  constructor(private service: ArrivalFlightService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArrivalFlight> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((arrivalFlight: HttpResponse<ArrivalFlight>) => {
          if (arrivalFlight.body) {
            return of(arrivalFlight.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArrivalFlight());
  }
}

export const arrivalFlightRoute: Routes = [
  {
    path: '',
    component: ArrivalFlightComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'aodbApp.flightArrivalFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArrivalFlightDetailComponent,
    resolve: {
      arrivalFlight: ArrivalFlightResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.flightArrivalFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArrivalFlightUpdateComponent,
    resolve: {
      arrivalFlight: ArrivalFlightResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.flightArrivalFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArrivalFlightUpdateComponent,
    resolve: {
      arrivalFlight: ArrivalFlightResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.flightArrivalFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
