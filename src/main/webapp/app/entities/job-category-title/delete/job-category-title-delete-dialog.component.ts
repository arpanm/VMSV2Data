import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobCategoryTitle } from '../job-category-title.model';
import { JobCategoryTitleService } from '../service/job-category-title.service';

@Component({
  templateUrl: './job-category-title-delete-dialog.component.html',
})
export class JobCategoryTitleDeleteDialogComponent {
  jobCategoryTitle?: IJobCategoryTitle;

  constructor(protected jobCategoryTitleService: JobCategoryTitleService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobCategoryTitleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
