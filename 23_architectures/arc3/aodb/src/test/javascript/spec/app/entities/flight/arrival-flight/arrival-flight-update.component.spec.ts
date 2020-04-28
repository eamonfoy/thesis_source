import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AodbTestModule } from '../../../../test.module';
import { ArrivalFlightUpdateComponent } from 'app/entities/flight/arrival-flight/arrival-flight-update.component';
import { ArrivalFlightService } from 'app/entities/flight/arrival-flight/arrival-flight.service';
import { ArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

describe('Component Tests', () => {
  describe('ArrivalFlight Management Update Component', () => {
    let comp: ArrivalFlightUpdateComponent;
    let fixture: ComponentFixture<ArrivalFlightUpdateComponent>;
    let service: ArrivalFlightService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [ArrivalFlightUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArrivalFlightUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArrivalFlightUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArrivalFlightService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArrivalFlight(123);
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
        const entity = new ArrivalFlight();
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
