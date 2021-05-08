import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoundationalDataType } from '../foundational-data-type.model';
import { FoundationalDataTypeService } from '../service/foundational-data-type.service';

@Component({
  templateUrl: './foundational-data-type-delete-dialog.component.html',
})
export class FoundationalDataTypeDeleteDialogComponent {
  foundationalDataType?: IFoundationalDataType;

  constructor(protected foundationalDataTypeService: FoundationalDataTypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.foundationalDataTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
