import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomFieldType } from '../custom-field-type.model';
import { CustomFieldTypeService } from '../service/custom-field-type.service';

@Component({
  templateUrl: './custom-field-type-delete-dialog.component.html',
})
export class CustomFieldTypeDeleteDialogComponent {
  customFieldType?: ICustomFieldType;

  constructor(protected customFieldTypeService: CustomFieldTypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customFieldTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
