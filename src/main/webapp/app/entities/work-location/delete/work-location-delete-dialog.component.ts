import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkLocation } from '../work-location.model';
import { WorkLocationService } from '../service/work-location.service';

@Component({
  templateUrl: './work-location-delete-dialog.component.html',
})
export class WorkLocationDeleteDialogComponent {
  workLocation?: IWorkLocation;

  constructor(protected workLocationService: WorkLocationService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workLocationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
