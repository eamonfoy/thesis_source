import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AodbTestModule } from '../../../../test.module';
import { DepartureFlightUpdateComponent } from 'app/entities/flight/departure-flight/departure-flight-update.component';
import { DepartureFlightService } from 'app/entities/flight/departure-flight/departure-flight.service';
import { DepartureFlight } from 'app/shared/model/flight/departure-flight.model';

describe('Component Tests', () => {
  describe('DepartureFlight Management Update Component', () => {
    let comp: DepartureFlightUpdateComponent;
    let fixture: ComponentFixture<DepartureFlightUpdateComponent>;
    let service: DepartureFlightService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [DepartureFlightUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepartureFlightUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartureFlightUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartureFlightService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepartureFlight(123);
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
        const entity = new DepartureFlight();
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
