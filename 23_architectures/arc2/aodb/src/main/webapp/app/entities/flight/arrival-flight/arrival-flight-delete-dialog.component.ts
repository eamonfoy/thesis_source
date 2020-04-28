import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';
import { ArrivalFlightService } from './arrival-flight.service';

@Component({
  templateUrl: './arrival-flight-delete-dialog.component.html'
})
export class ArrivalFlightDeleteDialogComponent {
  arrivalFlight?: IArrivalFlight;

  constructor(
    protected arrivalFlightService: ArrivalFlightService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arrivalFlightService.delete(id).subscribe(() => {
      this.eventManager.broadcast('arrivalFlightListModification');
      this.activeModal.close();
    });
  }
}
