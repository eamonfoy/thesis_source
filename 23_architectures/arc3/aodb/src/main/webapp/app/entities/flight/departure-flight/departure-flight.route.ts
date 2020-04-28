import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDepartureFlight, DepartureFlight } from 'app/shared/model/flight/departure-flight.model';
import { DepartureFlightService } from './departure-flight.service';
import { DepartureFlightComponent } from './departure-flight.component';
import { DepartureFlightDetailComponent } from './departure-flight-detail.component';
import { DepartureFlightUpdateComponent } from './departure-flight-update.component';

@Injectable({ providedIn: 'root' })
export class DepartureFlightResolve implements Resolve<IDepartureFlight> {
  constructor(private service: DepartureFlightService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepartureFlight> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((departureFlight: HttpResponse<DepartureFlight>) => {
          if (departureFlight.body) {
            return of(departureFlight.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DepartureFlight());
  }
}

export const departureFlightRoute: Routes = [
  {
    path: '',
    component: DepartureFlightComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'aodbApp.flightDepartureFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartureFlightDetailComponent,
    resolve: {
      departureFlight: DepartureFlightResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.flightDepartureFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartureFlightUpdateComponent,
    resolve: {
      departureFlight: DepartureFlightResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.flightDepartureFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartureFlightUpdateComponent,
    resolve: {
      departureFlight: DepartureFlightResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.flightDepartureFlight.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
