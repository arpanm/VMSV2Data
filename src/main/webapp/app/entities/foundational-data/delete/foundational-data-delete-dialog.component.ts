import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoundationalData } from '../foundational-data.model';
import { FoundationalDataService } from '../service/foundational-data.service';

@Component({
  templateUrl: './foundational-data-delete-dialog.component.html',
})
export class FoundationalDataDeleteDialogComponent {
  foundationalData?: IFoundationalData;

  constructor(protected foundationalDataService: FoundationalDataService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.foundationalDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
