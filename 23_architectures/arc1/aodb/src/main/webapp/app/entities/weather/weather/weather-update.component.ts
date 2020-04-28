import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWeather, Weather } from 'app/shared/model/weather/weather.model';
import { WeatherService } from './weather.service';

@Component({
  selector: 'jhi-weather-update',
  templateUrl: './weather-update.component.html'
})
export class WeatherUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    airportCode: [null, [Validators.required]],
    forecastDate: [null, [Validators.required]],
    dayName: [null, [Validators.required]],
    highTemperatureValue: [null, [Validators.required]],
    lowTemperatureValue: [null, [Validators.required]],
    feelsLikeHighTemperature: [null, [Validators.required]],
    feelsLikeLowTemperature: [null, [Validators.required]],
    phrase: [null, [Validators.required]],
    probabilityOfPrecip: [null, [Validators.required]],
    probabilityOfPrecipUnits: [null, [Validators.required]],
    nightPhrase: [null, [Validators.required]],
    nightIcon: [null, [Validators.required]],
    nightProbabilityOfPrecip: [null, [Validators.required]],
    nightProbabilityOfPrecipUnits: [null, [Validators.required]],
    icon: [null, [Validators.required]]
  });

  constructor(protected weatherService: WeatherService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weather }) => {
      if (!weather.id) {
        const today = moment().startOf('day');
        weather.forecastDate = today;
      }

      this.updateForm(weather);
    });
  }

  updateForm(weather: IWeather): void {
    this.editForm.patchValue({
      id: weather.id,
      airportCode: weather.airportCode,
      forecastDate: weather.forecastDate ? weather.forecastDate.format(DATE_TIME_FORMAT) : null,
      dayName: weather.dayName,
      highTemperatureValue: weather.highTemperatureValue,
      lowTemperatureValue: weather.lowTemperatureValue,
      feelsLikeHighTemperature: weather.feelsLikeHighTemperature,
      feelsLikeLowTemperature: weather.feelsLikeLowTemperature,
      phrase: weather.phrase,
      probabilityOfPrecip: weather.probabilityOfPrecip,
      probabilityOfPrecipUnits: weather.probabilityOfPrecipUnits,
      nightPhrase: weather.nightPhrase,
      nightIcon: weather.nightIcon,
      nightProbabilityOfPrecip: weather.nightProbabilityOfPrecip,
      nightProbabilityOfPrecipUnits: weather.nightProbabilityOfPrecipUnits,
      icon: weather.icon
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const weather = this.createFromForm();
    if (weather.id !== undefined) {
      this.subscribeToSaveResponse(this.weatherService.update(weather));
    } else {
      this.subscribeToSaveResponse(this.weatherService.create(weather));
    }
  }

  private createFromForm(): IWeather {
    return {
      ...new Weather(),
      id: this.editForm.get(['id'])!.value,
      airportCode: this.editForm.get(['airportCode'])!.value,
      forecastDate: this.editForm.get(['forecastDate'])!.value
        ? moment(this.editForm.get(['forecastDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dayName: this.editForm.get(['dayName'])!.value,
      highTemperatureValue: this.editForm.get(['highTemperatureValue'])!.value,
      lowTemperatureValue: this.editForm.get(['lowTemperatureValue'])!.value,
      feelsLikeHighTemperature: this.editForm.get(['feelsLikeHighTemperature'])!.value,
      feelsLikeLowTemperature: this.editForm.get(['feelsLikeLowTemperature'])!.value,
      phrase: this.editForm.get(['phrase'])!.value,
      probabilityOfPrecip: this.editForm.get(['probabilityOfPrecip'])!.value,
      probabilityOfPrecipUnits: this.editForm.get(['probabilityOfPrecipUnits'])!.value,
      nightPhrase: this.editForm.get(['nightPhrase'])!.value,
      nightIcon: this.editForm.get(['nightIcon'])!.value,
      nightProbabilityOfPrecip: this.editForm.get(['nightProbabilityOfPrecip'])!.value,
      nightProbabilityOfPrecipUnits: this.editForm.get(['nightProbabilityOfPrecipUnits'])!.value,
      icon: this.editForm.get(['icon'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeather>>): void {
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
