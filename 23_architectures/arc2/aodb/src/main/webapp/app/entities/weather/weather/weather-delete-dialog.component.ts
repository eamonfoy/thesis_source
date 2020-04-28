import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeather } from 'app/shared/model/weather/weather.model';
import { WeatherService } from './weather.service';

@Component({
  templateUrl: './weather-delete-dialog.component.html'
})
export class WeatherDeleteDialogComponent {
  weather?: IWeather;

  constructor(protected weatherService: WeatherService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.weatherService.delete(id).subscribe(() => {
      this.eventManager.broadcast('weatherListModification');
      this.activeModal.close();
    });
  }
}
