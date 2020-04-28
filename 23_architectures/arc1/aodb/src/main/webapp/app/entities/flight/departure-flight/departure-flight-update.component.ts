import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDepartureFlight, DepartureFlight } from 'app/shared/model/flight/departure-flight.model';
import { DepartureFlightService } from './departure-flight.service';

@Component({
  selector: 'jhi-departure-flight-update',
  templateUrl: './departure-flight-update.component.html'
})
export class DepartureFlightUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    actual: [],
    estimated: [],
    scheduled: [null, [Validators.required]],
    city: [null, [Validators.required]],
    aircraft: [null, [Validators.required]],
    terminal: [null, [Validators.required]],
    duration: [null, [Validators.required]],
    tailNumber: [null, [Validators.required]],
    airportCode: [null, [Validators.required]],
    airline: [null, [Validators.required]],
    flightNumber: [null, [Validators.required]],
    gate: [null, [Validators.required]],
    status: [null, [Validators.required]],
    statusText: [null, [Validators.required]]
  });

  constructor(
    protected departureFlightService: DepartureFlightService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departureFlight }) => {
      if (!departureFlight.id) {
        const today = moment().startOf('day');
        departureFlight.actual = today;
        departureFlight.estimated = today;
        departureFlight.scheduled = today;
      }

      this.updateForm(departureFlight);
    });
  }

  updateForm(departureFlight: IDepartureFlight): void {
    this.editForm.patchValue({
      id: departureFlight.id,
      actual: departureFlight.actual ? departureFlight.actual.format(DATE_TIME_FORMAT) : null,
      estimated: departureFlight.estimated ? departureFlight.estimated.format(DATE_TIME_FORMAT) : null,
      scheduled: departureFlight.scheduled ? departureFlight.scheduled.format(DATE_TIME_FORMAT) : null,
      city: departureFlight.city,
      aircraft: departureFlight.aircraft,
      terminal: departureFlight.terminal,
      duration: departureFlight.duration,
      tailNumber: departureFlight.tailNumber,
      airportCode: departureFlight.airportCode,
      airline: departureFlight.airline,
      flightNumber: departureFlight.flightNumber,
      gate: departureFlight.gate,
      status: departureFlight.status,
      statusText: departureFlight.statusText
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departureFlight = this.createFromForm();
    if (departureFlight.id !== undefined) {
      this.subscribeToSaveResponse(this.departureFlightService.update(departureFlight));
    } else {
      this.subscribeToSaveResponse(this.departureFlightService.create(departureFlight));
    }
  }

  private createFromForm(): IDepartureFlight {
    return {
      ...new DepartureFlight(),
      id: this.editForm.get(['id'])!.value,
      actual: this.editForm.get(['actual'])!.value ? moment(this.editForm.get(['actual'])!.value, DATE_TIME_FORMAT) : undefined,
      estimated: this.editForm.get(['estimated'])!.value ? moment(this.editForm.get(['estimated'])!.value, DATE_TIME_FORMAT) : undefined,
      scheduled: this.editForm.get(['scheduled'])!.value ? moment(this.editForm.get(['scheduled'])!.value, DATE_TIME_FORMAT) : undefined,
      city: this.editForm.get(['city'])!.value,
      aircraft: this.editForm.get(['aircraft'])!.value,
      terminal: this.editForm.get(['terminal'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      tailNumber: this.editForm.get(['tailNumber'])!.value,
      airportCode: this.editForm.get(['airportCode'])!.value,
      airline: this.editForm.get(['airline'])!.value,
      flightNumber: this.editForm.get(['flightNumber'])!.value,
      gate: this.editForm.get(['gate'])!.value,
      status: this.editForm.get(['status'])!.value,
      statusText: this.editForm.get(['statusText'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartureFlight>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
