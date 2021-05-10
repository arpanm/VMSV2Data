import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { JobTemplateComponent } from './list/job-template.component';
import { JobTemplateDetailComponent } from './detail/job-template-detail.component';
import { JobTemplateUpdateComponent } from './update/job-template-update.component';
import { JobTemplateDeleteDialogComponent } from './delete/job-template-delete-dialog.component';
import { JobTemplateRoutingModule } from './route/job-template-routing.module';

@NgModule({
  imports: [SharedModule, JobTemplateRoutingModule],
  declarations: [JobTemplateComponent, JobTemplateDetailComponent, JobTemplateUpdateComponent, JobTemplateDeleteDialogComponent],
  entryComponents: [JobTemplateDeleteDialogComponent],
})
export class JobTemplateModule {}
