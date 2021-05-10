import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ProgramUserComponent } from './list/program-user.component';
import { ProgramUserDetailComponent } from './detail/program-user-detail.component';
import { ProgramUserUpdateComponent } from './update/program-user-update.component';
import { ProgramUserDeleteDialogComponent } from './delete/program-user-delete-dialog.component';
import { ProgramUserRoutingModule } from './route/program-user-routing.module';

@NgModule({
  imports: [SharedModule, ProgramUserRoutingModule],
  declarations: [ProgramUserComponent, ProgramUserDetailComponent, ProgramUserUpdateComponent, ProgramUserDeleteDialogComponent],
  entryComponents: [ProgramUserDeleteDialogComponent],
})
export class ProgramUserModule {}
