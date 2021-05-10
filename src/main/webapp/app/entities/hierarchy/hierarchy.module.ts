import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { HierarchyComponent } from './list/hierarchy.component';
import { HierarchyDetailComponent } from './detail/hierarchy-detail.component';
import { HierarchyUpdateComponent } from './update/hierarchy-update.component';
import { HierarchyDeleteDialogComponent } from './delete/hierarchy-delete-dialog.component';
import { HierarchyRoutingModule } from './route/hierarchy-routing.module';

@NgModule({
  imports: [SharedModule, HierarchyRoutingModule],
  declarations: [HierarchyComponent, HierarchyDetailComponent, HierarchyUpdateComponent, HierarchyDeleteDialogComponent],
  entryComponents: [HierarchyDeleteDialogComponent],
})
export class HierarchyModule {}
