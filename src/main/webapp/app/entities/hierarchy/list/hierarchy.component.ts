import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHierarchy } from '../hierarchy.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { HierarchyService } from '../service/hierarchy.service';
import { HierarchyDeleteDialogComponent } from '../delete/hierarchy-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-hierarchy',
  templateUrl: './hierarchy.component.html',
})
export class HierarchyComponent implements OnInit {
  hierarchies: IHierarchy[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected hierarchyService: HierarchyService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.hierarchies = [];
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

    this.hierarchyService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IHierarchy[]>) => {
          this.isLoading = false;
          this.paginateHierarchies(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.hierarchies = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHierarchy): number {
    return item.id!;
  }

  delete(hierarchy: IHierarchy): void {
    const modalRef = this.modalService.open(HierarchyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hierarchy = hierarchy;
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

  protected paginateHierarchies(data: IHierarchy[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.hierarchies.push(d);
      }
    }
  }
}
