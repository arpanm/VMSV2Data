import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJobTemplateRateCard } from '../job-template-rate-card.model';
import { JobTemplateRateCardService } from '../service/job-template-rate-card.service';

@Component({
  templateUrl: './job-template-rate-card-delete-dialog.component.html',
})
export class JobTemplateRateCardDeleteDialogComponent {
  jobTemplateRateCard?: IJobTemplateRateCard;

  constructor(protected jobTemplateRateCardService: JobTemplateRateCardService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobTemplateRateCardService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
