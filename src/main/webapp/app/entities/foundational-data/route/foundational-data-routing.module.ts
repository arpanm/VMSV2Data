import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FoundationalDataComponent } from '../list/foundational-data.component';
import { FoundationalDataDetailComponent } from '../detail/foundational-data-detail.component';
import { FoundationalDataUpdateComponent } from '../update/foundational-data-update.component';
import { FoundationalDataRoutingResolveService } from './foundational-data-routing-resolve.service';

const foundationalDataRoute: Routes = [
  {
    path: '',
    component: FoundationalDataComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FoundationalDataDetailComponent,
    resolve: {
      foundationalData: FoundationalDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FoundationalDataUpdateComponent,
    resolve: {
      foundationalData: FoundationalDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FoundationalDataUpdateComponent,
    resolve: {
      foundationalData: FoundationalDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(foundationalDataRoute)],
  exports: [RouterModule],
})
export class FoundationalDataRoutingModule {}
