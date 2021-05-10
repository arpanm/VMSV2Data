import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { WorkLocationComponent } from './list/work-location.component';
import { WorkLocationDetailComponent } from './detail/work-location-detail.component';
import { WorkLocationUpdateComponent } from './update/work-location-update.component';
import { WorkLocationDeleteDialogComponent } from './delete/work-location-delete-dialog.component';
import { WorkLocationRoutingModule } from './route/work-location-routing.module';

@NgModule({
  imports: [SharedModule, WorkLocationRoutingModule],
  declarations: [WorkLocationComponent, WorkLocationDetailComponent, WorkLocationUpdateComponent, WorkLocationDeleteDialogComponent],
  entryComponents: [WorkLocationDeleteDialogComponent],
})
export class WorkLocationModule {}
