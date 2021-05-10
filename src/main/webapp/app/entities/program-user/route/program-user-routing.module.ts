import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramUserComponent } from '../list/program-user.component';
import { ProgramUserDetailComponent } from '../detail/program-user-detail.component';
import { ProgramUserUpdateComponent } from '../update/program-user-update.component';
import { ProgramUserRoutingResolveService } from './program-user-routing-resolve.service';

const programUserRoute: Routes = [
  {
    path: '',
    component: ProgramUserComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramUserDetailComponent,
    resolve: {
      programUser: ProgramUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramUserUpdateComponent,
    resolve: {
      programUser: ProgramUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramUserUpdateComponent,
    resolve: {
      programUser: ProgramUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programUserRoute)],
  exports: [RouterModule],
})
export class ProgramUserRoutingModule {}
