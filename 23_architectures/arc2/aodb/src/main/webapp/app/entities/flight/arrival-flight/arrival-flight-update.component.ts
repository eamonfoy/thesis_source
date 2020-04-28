import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IArrivalFlight, ArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';
import { ArrivalFlightService } from './arrival-flight.service';

@Component({
  selector: 'jhi-arrival-flight-update',
  templateUrl: './arrival-flight-update.component.html'
})
export class ArrivalFlightUpdateComponent implements OnInit {
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
    claim: [],
    status: [null, [Validators.required]],
    statusText: [null, [Validators.required]]
  });

  constructor(protected arrivalFlightService: ArrivalFlightService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrivalFlight }) => {
      if (!arrivalFlight.id) {
        const today = moment().startOf('day');
        arrivalFlight.actual = today;
        arrivalFlight.estimated = today;
        arrivalFlight.scheduled = today;
      }

      this.updateForm(arrivalFlight);
    });
  }

  updateForm(arrivalFlight: IArrivalFlight): void {
    this.editForm.patchValue({
      id: arrivalFlight.id,
      actual: arrivalFlight.actual ? arrivalFlight.actual.format(DATE_TIME_FORMAT) : null,
      estimated: arrivalFlight.estimated ? arrivalFlight.estimated.format(DATE_TIME_FORMAT) : null,
      scheduled: arrivalFlight.scheduled ? arrivalFlight.scheduled.format(DATE_TIME_FORMAT) : null,
      city: arrivalFlight.city,
      aircraft: arrivalFlight.aircraft,
      terminal: arrivalFlight.terminal,
      duration: arrivalFlight.duration,
      tailNumber: arrivalFlight.tailNumber,
      airportCode: arrivalFlight.airportCode,
      airline: arrivalFlight.airline,
      flightNumber: arrivalFlight.flightNumber,
      claim: arrivalFlight.claim,
      status: arrivalFlight.status,
      statusText: arrivalFlight.statusText
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arrivalFlight = this.createFromForm();
    if (arrivalFlight.id !== undefined) {
      this.subscribeToSaveResponse(this.arrivalFlightService.update(arrivalFlight));
    } else {
      this.subscribeToSaveResponse(this.arrivalFlightService.create(arrivalFlight));
    }
  }

  private createFromForm(): IArrivalFlight {
    return {
      ...new ArrivalFlight(),
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
      claim: this.editForm.get(['claim'])!.value,
      status: this.editForm.get(['status'])!.value,
      statusText: this.editForm.get(['statusText'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArrivalFlight>>): void {
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
