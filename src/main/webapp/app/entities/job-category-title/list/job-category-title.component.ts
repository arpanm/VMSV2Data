import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobCategoryTitle } from '../job-category-title.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { JobCategoryTitleService } from '../service/job-category-title.service';
import { JobCategoryTitleDeleteDialogComponent } from '../delete/job-category-title-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-job-category-title',
  templateUrl: './job-category-title.component.html',
})
export class JobCategoryTitleComponent implements OnInit {
  jobCategoryTitles: IJobCategoryTitle[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected jobCategoryTitleService: JobCategoryTitleService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.jobCategoryTitles = [];
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

    this.jobCategoryTitleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IJobCategoryTitle[]>) => {
          this.isLoading = false;
          this.paginateJobCategoryTitles(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.jobCategoryTitles = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IJobCategoryTitle): number {
    return item.id!;
  }

  delete(jobCategoryTitle: IJobCategoryTitle): void {
    const modalRef = this.modalService.open(JobCategoryTitleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobCategoryTitle = jobCategoryTitle;
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

  protected paginateJobCategoryTitles(data: IJobCategoryTitle[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.jobCategoryTitles.push(d);
      }
    }
  }
}
