import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WorkLocationComponent } from '../list/work-location.component';
import { WorkLocationDetailComponent } from '../detail/work-location-detail.component';
import { WorkLocationUpdateComponent } from '../update/work-location-update.component';
import { WorkLocationRoutingResolveService } from './work-location-routing-resolve.service';

const workLocationRoute: Routes = [
  {
    path: '',
    component: WorkLocationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkLocationDetailComponent,
    resolve: {
      workLocation: WorkLocationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkLocationUpdateComponent,
    resolve: {
      workLocation: WorkLocationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkLocationUpdateComponent,
    resolve: {
      workLocation: WorkLocationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(workLocationRoute)],
  exports: [RouterModule],
})
export class WorkLocationRoutingModule {}
