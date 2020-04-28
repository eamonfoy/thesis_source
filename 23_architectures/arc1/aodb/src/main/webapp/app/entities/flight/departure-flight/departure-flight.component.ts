import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepartureFlight } from 'app/shared/model/flight/departure-flight.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DepartureFlightService } from './departure-flight.service';
import { DepartureFlightDeleteDialogComponent } from './departure-flight-delete-dialog.component';

@Component({
  selector: 'jhi-departure-flight',
  templateUrl: './departure-flight.component.html'
})
export class DepartureFlightComponent implements OnInit, OnDestroy {
  departureFlights?: IDepartureFlight[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected departureFlightService: DepartureFlightService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.departureFlightService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDepartureFlight[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInDepartureFlights();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDepartureFlight): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDepartureFlights(): void {
    this.eventSubscriber = this.eventManager.subscribe('departureFlightListModification', () => this.loadPage());
  }

  delete(departureFlight: IDepartureFlight): void {
    const modalRef = this.modalService.open(DepartureFlightDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.departureFlight = departureFlight;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDepartureFlight[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/departure-flight'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.departureFlights = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
