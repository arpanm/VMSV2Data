import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobTemplate } from '../job-template.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { JobTemplateService } from '../service/job-template.service';
import { JobTemplateDeleteDialogComponent } from '../delete/job-template-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-job-template',
  templateUrl: './job-template.component.html',
})
export class JobTemplateComponent implements OnInit {
  jobTemplates: IJobTemplate[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected jobTemplateService: JobTemplateService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.jobTemplates = [];
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

    this.jobTemplateService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IJobTemplate[]>) => {
          this.isLoading = false;
          this.paginateJobTemplates(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.jobTemplates = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IJobTemplate): number {
    return item.id!;
  }

  delete(jobTemplate: IJobTemplate): void {
    const modalRef = this.modalService.open(JobTemplateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobTemplate = jobTemplate;
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

  protected paginateJobTemplates(data: IJobTemplate[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.jobTemplates.push(d);
      }
    }
  }
}
