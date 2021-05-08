import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomFieldType } from '../custom-field-type.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { CustomFieldTypeService } from '../service/custom-field-type.service';
import { CustomFieldTypeDeleteDialogComponent } from '../delete/custom-field-type-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-custom-field-type',
  templateUrl: './custom-field-type.component.html',
})
export class CustomFieldTypeComponent implements OnInit {
  customFieldTypes: ICustomFieldType[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected customFieldTypeService: CustomFieldTypeService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.customFieldTypes = [];
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

    this.customFieldTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ICustomFieldType[]>) => {
          this.isLoading = false;
          this.paginateCustomFieldTypes(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.customFieldTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICustomFieldType): number {
    return item.id!;
  }

  delete(customFieldType: ICustomFieldType): void {
    const modalRef = this.modalService.open(CustomFieldTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customFieldType = customFieldType;
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

  protected paginateCustomFieldTypes(data: ICustomFieldType[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.customFieldTypes.push(d);
      }
    }
  }
}
