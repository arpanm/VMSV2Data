import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { JobCategoryTitleComponent } from './list/job-category-title.component';
import { JobCategoryTitleDetailComponent } from './detail/job-category-title-detail.component';
import { JobCategoryTitleUpdateComponent } from './update/job-category-title-update.component';
import { JobCategoryTitleDeleteDialogComponent } from './delete/job-category-title-delete-dialog.component';
import { JobCategoryTitleRoutingModule } from './route/job-category-title-routing.module';

@NgModule({
  imports: [SharedModule, JobCategoryTitleRoutingModule],
  declarations: [
    JobCategoryTitleComponent,
    JobCategoryTitleDetailComponent,
    JobCategoryTitleUpdateComponent,
    JobCategoryTitleDeleteDialogComponent,
  ],
  entryComponents: [JobCategoryTitleDeleteDialogComponent],
})
export class JobCategoryTitleModule {}
