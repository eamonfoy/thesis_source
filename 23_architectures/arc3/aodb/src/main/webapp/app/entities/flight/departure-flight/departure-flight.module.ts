import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AodbSharedModule } from 'app/shared/shared.module';
import { DepartureFlightComponent } from './departure-flight.component';
import { DepartureFlightDetailComponent } from './departure-flight-detail.component';
import { DepartureFlightUpdateComponent } from './departure-flight-update.component';
import { DepartureFlightDeleteDialogComponent } from './departure-flight-delete-dialog.component';
import { departureFlightRoute } from './departure-flight.route';

@NgModule({
  imports: [AodbSharedModule, RouterModule.forChild(departureFlightRoute)],
  declarations: [
    DepartureFlightComponent,
    DepartureFlightDetailComponent,
    DepartureFlightUpdateComponent,
    DepartureFlightDeleteDialogComponent
  ],
  entryComponents: [DepartureFlightDeleteDialogComponent]
})
export class FlightDepartureFlightModule {}
