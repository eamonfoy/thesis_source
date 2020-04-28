import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AodbTestModule } from '../../../../test.module';
import { WeatherDetailComponent } from 'app/entities/weather/weather/weather-detail.component';
import { Weather } from 'app/shared/model/weather/weather.model';

describe('Component Tests', () => {
  describe('Weather Management Detail Component', () => {
    let comp: WeatherDetailComponent;
    let fixture: ComponentFixture<WeatherDetailComponent>;
    const route = ({ data: of({ weather: new Weather(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [WeatherDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WeatherDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WeatherDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load weather on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.weather).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
