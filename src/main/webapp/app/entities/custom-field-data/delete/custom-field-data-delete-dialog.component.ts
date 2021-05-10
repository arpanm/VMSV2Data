import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomFieldData } from '../custom-field-data.model';
import { CustomFieldDataService } from '../service/custom-field-data.service';

@Component({
  templateUrl: './custom-field-data-delete-dialog.component.html',
})
export class CustomFieldDataDeleteDialogComponent {
  customFieldData?: ICustomFieldData;

  constructor(protected customFieldDataService: CustomFieldDataService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customFieldDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
