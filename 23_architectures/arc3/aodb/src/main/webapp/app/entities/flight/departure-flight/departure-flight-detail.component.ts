import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartureFlight } from 'app/shared/model/flight/departure-flight.model';

@Component({
  selector: 'jhi-departure-flight-detail',
  templateUrl: './departure-flight-detail.component.html'
})
export class DepartureFlightDetailComponent implements OnInit {
  departureFlight: IDepartureFlight | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departureFlight }) => (this.departureFlight = departureFlight));
  }

  previousState(): void {
    window.history.back();
  }
}
