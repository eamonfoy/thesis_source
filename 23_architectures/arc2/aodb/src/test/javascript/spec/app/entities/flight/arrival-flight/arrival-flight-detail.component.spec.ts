import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AodbTestModule } from '../../../../test.module';
import { ArrivalFlightDetailComponent } from 'app/entities/flight/arrival-flight/arrival-flight-detail.component';
import { ArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

describe('Component Tests', () => {
  describe('ArrivalFlight Management Detail Component', () => {
    let comp: ArrivalFlightDetailComponent;
    let fixture: ComponentFixture<ArrivalFlightDetailComponent>;
    const route = ({ data: of({ arrivalFlight: new ArrivalFlight(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [ArrivalFlightDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArrivalFlightDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArrivalFlightDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load arrivalFlight on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.arrivalFlight).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
