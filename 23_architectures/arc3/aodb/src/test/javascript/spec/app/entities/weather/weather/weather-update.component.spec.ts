import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AodbTestModule } from '../../../../test.module';
import { WeatherUpdateComponent } from 'app/entities/weather/weather/weather-update.component';
import { WeatherService } from 'app/entities/weather/weather/weather.service';
import { Weather } from 'app/shared/model/weather/weather.model';

describe('Component Tests', () => {
  describe('Weather Management Update Component', () => {
    let comp: WeatherUpdateComponent;
    let fixture: ComponentFixture<WeatherUpdateComponent>;
    let service: WeatherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [WeatherUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WeatherUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeatherUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeatherService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Weather(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Weather();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
