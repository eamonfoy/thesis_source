import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

@Component({
  selector: 'jhi-arrival-flight-detail',
  templateUrl: './arrival-flight-detail.component.html'
})
export class ArrivalFlightDetailComponent implements OnInit {
  arrivalFlight: IArrivalFlight | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrivalFlight }) => (this.arrivalFlight = arrivalFlight));
  }

  previousState(): void {
    window.history.back();
  }
}
