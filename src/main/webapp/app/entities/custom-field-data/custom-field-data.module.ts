import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CustomFieldDataComponent } from './list/custom-field-data.component';
import { CustomFieldDataDetailComponent } from './detail/custom-field-data-detail.component';
import { CustomFieldDataUpdateComponent } from './update/custom-field-data-update.component';
import { CustomFieldDataDeleteDialogComponent } from './delete/custom-field-data-delete-dialog.component';
import { CustomFieldDataRoutingModule } from './route/custom-field-data-routing.module';

@NgModule({
  imports: [SharedModule, CustomFieldDataRoutingModule],
  declarations: [
    CustomFieldDataComponent,
    CustomFieldDataDetailComponent,
    CustomFieldDataUpdateComponent,
    CustomFieldDataDeleteDialogComponent,
  ],
  entryComponents: [CustomFieldDataDeleteDialogComponent],
})
export class CustomFieldDataModule {}
