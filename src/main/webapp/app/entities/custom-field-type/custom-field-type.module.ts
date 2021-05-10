import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CustomFieldTypeComponent } from './list/custom-field-type.component';
import { CustomFieldTypeDetailComponent } from './detail/custom-field-type-detail.component';
import { CustomFieldTypeUpdateComponent } from './update/custom-field-type-update.component';
import { CustomFieldTypeDeleteDialogComponent } from './delete/custom-field-type-delete-dialog.component';
import { CustomFieldTypeRoutingModule } from './route/custom-field-type-routing.module';

@NgModule({
  imports: [SharedModule, CustomFieldTypeRoutingModule],
  declarations: [
    CustomFieldTypeComponent,
    CustomFieldTypeDetailComponent,
    CustomFieldTypeUpdateComponent,
    CustomFieldTypeDeleteDialogComponent,
  ],
  entryComponents: [CustomFieldTypeDeleteDialogComponent],
})
export class CustomFieldTypeModule {}
