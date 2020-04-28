import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWeather, Weather } from 'app/shared/model/weather/weather.model';
import { WeatherService } from './weather.service';
import { WeatherComponent } from './weather.component';
import { WeatherDetailComponent } from './weather-detail.component';
import { WeatherUpdateComponent } from './weather-update.component';

@Injectable({ providedIn: 'root' })
export class WeatherResolve implements Resolve<IWeather> {
  constructor(private service: WeatherService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeather> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((weather: HttpResponse<Weather>) => {
          if (weather.body) {
            return of(weather.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Weather());
  }
}

export const weatherRoute: Routes = [
  {
    path: '',
    component: WeatherComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'aodbApp.weatherWeather.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WeatherDetailComponent,
    resolve: {
      weather: WeatherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.weatherWeather.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WeatherUpdateComponent,
    resolve: {
      weather: WeatherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.weatherWeather.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WeatherUpdateComponent,
    resolve: {
      weather: WeatherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'aodbApp.weatherWeather.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
