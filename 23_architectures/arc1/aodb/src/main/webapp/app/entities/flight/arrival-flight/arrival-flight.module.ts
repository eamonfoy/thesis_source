import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AodbSharedModule } from 'app/shared/shared.module';
import { ArrivalFlightComponent } from './arrival-flight.component';
import { ArrivalFlightDetailComponent } from './arrival-flight-detail.component';
import { ArrivalFlightUpdateComponent } from './arrival-flight-update.component';
import { ArrivalFlightDeleteDialogComponent } from './arrival-flight-delete-dialog.component';
import { arrivalFlightRoute } from './arrival-flight.route';

@NgModule({
  imports: [AodbSharedModule, RouterModule.forChild(arrivalFlightRoute)],
  declarations: [ArrivalFlightComponent, ArrivalFlightDetailComponent, ArrivalFlightUpdateComponent, ArrivalFlightDeleteDialogComponent],
  entryComponents: [ArrivalFlightDeleteDialogComponent]
})
export class FlightArrivalFlightModule {}
