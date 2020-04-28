import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'weather',
        loadChildren: () => import('./weather/weather/weather.module').then(m => m.WeatherWeatherModule)
      },
      {
        path: 'arrival-flight',
        loadChildren: () => import('./flight/arrival-flight/arrival-flight.module').then(m => m.FlightArrivalFlightModule)
      },
      {
        path: 'departure-flight',
        loadChildren: () => import('./flight/departure-flight/departure-flight.module').then(m => m.FlightDepartureFlightModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AodbEntityModule {}
