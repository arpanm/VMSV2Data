import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobTemplateRateCard } from '../job-template-rate-card.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { JobTemplateRateCardService } from '../service/job-template-rate-card.service';
import { JobTemplateRateCardDeleteDialogComponent } from '../delete/job-template-rate-card-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-job-template-rate-card',
  templateUrl: './job-template-rate-card.component.html',
})
export class JobTemplateRateCardComponent implements OnInit {
  jobTemplateRateCards: IJobTemplateRateCard[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected jobTemplateRateCardService: JobTemplateRateCardService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.jobTemplateRateCards = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.jobTemplateRateCardService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IJobTemplateRateCard[]>) => {
          this.isLoading = false;
          this.paginateJobTemplateRateCards(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.jobTemplateRateCards = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IJobTemplateRateCard): number {
    return item.id!;
  }

  delete(jobTemplateRateCard: IJobTemplateRateCard): void {
    const modalRef = this.modalService.open(JobTemplateRateCardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobTemplateRateCard = jobTemplateRateCard;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateJobTemplateRateCards(data: IJobTemplateRateCard[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.jobTemplateRateCards.push(d);
      }
    }
  }
}
