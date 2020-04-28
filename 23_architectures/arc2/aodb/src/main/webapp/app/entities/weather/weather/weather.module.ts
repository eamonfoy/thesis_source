import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AodbSharedModule } from 'app/shared/shared.module';
import { WeatherComponent } from './weather.component';
import { WeatherDetailComponent } from './weather-detail.component';
import { WeatherUpdateComponent } from './weather-update.component';
import { WeatherDeleteDialogComponent } from './weather-delete-dialog.component';
import { weatherRoute } from './weather.route';

@NgModule({
  imports: [AodbSharedModule, RouterModule.forChild(weatherRoute)],
  declarations: [WeatherComponent, WeatherDetailComponent, WeatherUpdateComponent, WeatherDeleteDialogComponent],
  entryComponents: [WeatherDeleteDialogComponent]
})
export class WeatherWeatherModule {}
