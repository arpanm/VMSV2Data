import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { JobTemplateRateCardComponent } from './list/job-template-rate-card.component';
import { JobTemplateRateCardDetailComponent } from './detail/job-template-rate-card-detail.component';
import { JobTemplateRateCardUpdateComponent } from './update/job-template-rate-card-update.component';
import { JobTemplateRateCardDeleteDialogComponent } from './delete/job-template-rate-card-delete-dialog.component';
import { JobTemplateRateCardRoutingModule } from './route/job-template-rate-card-routing.module';

@NgModule({
  imports: [SharedModule, JobTemplateRateCardRoutingModule],
  declarations: [
    JobTemplateRateCardComponent,
    JobTemplateRateCardDetailComponent,
    JobTemplateRateCardUpdateComponent,
    JobTemplateRateCardDeleteDialogComponent,
  ],
  entryComponents: [JobTemplateRateCardDeleteDialogComponent],
})
export class JobTemplateRateCardModule {}
