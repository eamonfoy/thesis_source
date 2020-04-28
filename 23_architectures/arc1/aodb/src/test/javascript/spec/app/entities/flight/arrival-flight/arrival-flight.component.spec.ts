import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { AodbTestModule } from '../../../../test.module';
import { ArrivalFlightComponent } from 'app/entities/flight/arrival-flight/arrival-flight.component';
import { ArrivalFlightService } from 'app/entities/flight/arrival-flight/arrival-flight.service';
import { ArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

describe('Component Tests', () => {
  describe('ArrivalFlight Management Component', () => {
    let comp: ArrivalFlightComponent;
    let fixture: ComponentFixture<ArrivalFlightComponent>;
    let service: ArrivalFlightService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [ArrivalFlightComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ArrivalFlightComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArrivalFlightComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArrivalFlightService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArrivalFlight(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.arrivalFlights && comp.arrivalFlights[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArrivalFlight(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.arrivalFlights && comp.arrivalFlights[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
