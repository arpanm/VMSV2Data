import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FoundationalDataComponent } from './list/foundational-data.component';
import { FoundationalDataDetailComponent } from './detail/foundational-data-detail.component';
import { FoundationalDataUpdateComponent } from './update/foundational-data-update.component';
import { FoundationalDataDeleteDialogComponent } from './delete/foundational-data-delete-dialog.component';
import { FoundationalDataRoutingModule } from './route/foundational-data-routing.module';

@NgModule({
  imports: [SharedModule, FoundationalDataRoutingModule],
  declarations: [
    FoundationalDataComponent,
    FoundationalDataDetailComponent,
    FoundationalDataUpdateComponent,
    FoundationalDataDeleteDialogComponent,
  ],
  entryComponents: [FoundationalDataDeleteDialogComponent],
})
export class FoundationalDataModule {}
