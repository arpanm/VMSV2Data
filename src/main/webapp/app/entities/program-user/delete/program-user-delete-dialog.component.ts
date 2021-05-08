import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramUser } from '../program-user.model';
import { ProgramUserService } from '../service/program-user.service';

@Component({
  templateUrl: './program-user-delete-dialog.component.html',
})
export class ProgramUserDeleteDialogComponent {
  programUser?: IProgramUser;

  constructor(protected programUserService: ProgramUserService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.programUserService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
