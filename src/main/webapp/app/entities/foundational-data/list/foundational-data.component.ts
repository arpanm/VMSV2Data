import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoundationalData } from '../foundational-data.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { FoundationalDataService } from '../service/foundational-data.service';
import { FoundationalDataDeleteDialogComponent } from '../delete/foundational-data-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-foundational-data',
  templateUrl: './foundational-data.component.html',
})
export class FoundationalDataComponent implements OnInit {
  foundationalData: IFoundationalData[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected foundationalDataService: FoundationalDataService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.foundationalData = [];
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

    this.foundationalDataService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IFoundationalData[]>) => {
          this.isLoading = false;
          this.paginateFoundationalData(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.foundationalData = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFoundationalData): number {
    return item.id!;
  }

  delete(foundationalData: IFoundationalData): void {
    const modalRef = this.modalService.open(FoundationalDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.foundationalData = foundationalData;
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

  protected paginateFoundationalData(data: IFoundationalData[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.foundationalData.push(d);
      }
    }
  }
}
