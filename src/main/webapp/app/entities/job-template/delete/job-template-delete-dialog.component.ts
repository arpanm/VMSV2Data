import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobTemplate } from '../job-template.model';
import { JobTemplateService } from '../service/job-template.service';

@Component({
  templateUrl: './job-template-delete-dialog.component.html',
})
export class JobTemplateDeleteDialogComponent {
  jobTemplate?: IJobTemplate;

  constructor(protected jobTemplateService: JobTemplateService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobTemplateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
