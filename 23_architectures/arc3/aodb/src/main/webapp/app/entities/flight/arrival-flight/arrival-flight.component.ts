import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IArrivalFlight } from 'app/shared/model/flight/arrival-flight.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ArrivalFlightService } from './arrival-flight.service';
import { ArrivalFlightDeleteDialogComponent } from './arrival-flight-delete-dialog.component';

@Component({
  selector: 'jhi-arrival-flight',
  templateUrl: './arrival-flight.component.html'
})
export class ArrivalFlightComponent implements OnInit, OnDestroy {
  arrivalFlights?: IArrivalFlight[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected arrivalFlightService: ArrivalFlightService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.arrivalFlightService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IArrivalFlight[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInArrivalFlights();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IArrivalFlight): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInArrivalFlights(): void {
    this.eventSubscriber = this.eventManager.subscribe('arrivalFlightListModification', () => this.loadPage());
  }

  delete(arrivalFlight: IArrivalFlight): void {
    const modalRef = this.modalService.open(ArrivalFlightDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.arrivalFlight = arrivalFlight;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IArrivalFlight[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/arrival-flight'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.arrivalFlights = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
