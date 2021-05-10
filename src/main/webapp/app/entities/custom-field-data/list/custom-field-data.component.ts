import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomFieldData } from '../custom-field-data.model';
import { CustomFieldDataService } from '../service/custom-field-data.service';
import { CustomFieldDataDeleteDialogComponent } from '../delete/custom-field-data-delete-dialog.component';

@Component({
  selector: 'jhi-custom-field-data',
  templateUrl: './custom-field-data.component.html',
})
export class CustomFieldDataComponent implements OnInit {
  customFieldData?: ICustomFieldData[];
  isLoading = false;

  constructor(protected customFieldDataService: CustomFieldDataService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.customFieldDataService.query().subscribe(
      (res: HttpResponse<ICustomFieldData[]>) => {
        this.isLoading = false;
        this.customFieldData = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICustomFieldData): number {
    return item.id!;
  }

  delete(customFieldData: ICustomFieldData): void {
    const modalRef = this.modalService.open(CustomFieldDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customFieldData = customFieldData;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
