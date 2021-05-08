import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FoundationalDataTypeComponent } from './list/foundational-data-type.component';
import { FoundationalDataTypeDetailComponent } from './detail/foundational-data-type-detail.component';
import { FoundationalDataTypeUpdateComponent } from './update/foundational-data-type-update.component';
import { FoundationalDataTypeDeleteDialogComponent } from './delete/foundational-data-type-delete-dialog.component';
import { FoundationalDataTypeRoutingModule } from './route/foundational-data-type-routing.module';

@NgModule({
  imports: [SharedModule, FoundationalDataTypeRoutingModule],
  declarations: [
    FoundationalDataTypeComponent,
    FoundationalDataTypeDetailComponent,
    FoundationalDataTypeUpdateComponent,
    FoundationalDataTypeDeleteDialogComponent,
  ],
  entryComponents: [FoundationalDataTypeDeleteDialogComponent],
})
export class FoundationalDataTypeModule {}
