import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoundationalDataType } from '../foundational-data-type.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { FoundationalDataTypeService } from '../service/foundational-data-type.service';
import { FoundationalDataTypeDeleteDialogComponent } from '../delete/foundational-data-type-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-foundational-data-type',
  templateUrl: './foundational-data-type.component.html',
})
export class FoundationalDataTypeComponent implements OnInit {
  foundationalDataTypes: IFoundationalDataType[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected foundationalDataTypeService: FoundationalDataTypeService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.foundationalDataTypes = [];
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

    this.foundationalDataTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IFoundationalDataType[]>) => {
          this.isLoading = false;
          this.paginateFoundationalDataTypes(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.foundationalDataTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFoundationalDataType): number {
    return item.id!;
  }

  delete(foundationalDataType: IFoundationalDataType): void {
    const modalRef = this.modalService.open(FoundationalDataTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.foundationalDataType = foundationalDataType;
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

  protected paginateFoundationalDataTypes(data: IFoundationalDataType[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.foundationalDataTypes.push(d);
      }
    }
  }
}
