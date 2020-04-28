import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AodbTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { ArrivalFlightDeleteDialogComponent } from 'app/entities/flight/arrival-flight/arrival-flight-delete-dialog.component';
import { ArrivalFlightService } from 'app/entities/flight/arrival-flight/arrival-flight.service';

describe('Component Tests', () => {
  describe('ArrivalFlight Management Delete Component', () => {
    let comp: ArrivalFlightDeleteDialogComponent;
    let fixture: ComponentFixture<ArrivalFlightDeleteDialogComponent>;
    let service: ArrivalFlightService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AodbTestModule],
        declarations: [ArrivalFlightDeleteDialogComponent]
      })
        .overrideTemplate(ArrivalFlightDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArrivalFlightDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArrivalFlightService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
