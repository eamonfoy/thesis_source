import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartureFlight } from 'app/shared/model/flight/departure-flight.model';
import { DepartureFlightService } from './departure-flight.service';

@Component({
  templateUrl: './departure-flight-delete-dialog.component.html'
})
export class DepartureFlightDeleteDialogComponent {
  departureFlight?: IDepartureFlight;

  constructor(
    protected departureFlightService: DepartureFlightService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departureFlightService.delete(id).subscribe(() => {
      this.eventManager.broadcast('departureFlightListModification');
      this.activeModal.close();
    });
  }
}
