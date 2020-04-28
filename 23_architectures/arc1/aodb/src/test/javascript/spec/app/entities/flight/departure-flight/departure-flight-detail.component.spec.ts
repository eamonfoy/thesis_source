import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AodbTestModule } from '../../../../test.module';
import { DepartureFlightDetailComponent } from 'app/entities/flight/departure-flight/departure-flight-detail.component';
import { DepartureFlight } from 'app/shared/model/flight/departure-flight.model';

describe('Component Tests', () => {
  describe('DepartureFlight Management Detail Component', () => {
    let comp: DepartureFlightDetailComponent;
    let fixture: ComponentFixture<DepartureFlightDetailComponent>;
    const route = ({ data: of({ departureFlight: new DepartureFlight(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [DepartureFlightDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepartureFlightDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartureFlightDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load departureFlight on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.departureFlight).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
