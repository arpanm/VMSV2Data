import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHierarchy } from '../hierarchy.model';
import { HierarchyService } from '../service/hierarchy.service';

@Component({
  templateUrl: './hierarchy-delete-dialog.component.html',
})
export class HierarchyDeleteDialogComponent {
  hierarchy?: IHierarchy;

  constructor(protected hierarchyService: HierarchyService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hierarchyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
