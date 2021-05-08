import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FoundationalDataTypeComponent } from '../list/foundational-data-type.component';
import { FoundationalDataTypeDetailComponent } from '../detail/foundational-data-type-detail.component';
import { FoundationalDataTypeUpdateComponent } from '../update/foundational-data-type-update.component';
import { FoundationalDataTypeRoutingResolveService } from './foundational-data-type-routing-resolve.service';

const foundationalDataTypeRoute: Routes = [
  {
    path: '',
    component: FoundationalDataTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FoundationalDataTypeDetailComponent,
    resolve: {
      foundationalDataType: FoundationalDataTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FoundationalDataTypeUpdateComponent,
    resolve: {
      foundationalDataType: FoundationalDataTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FoundationalDataTypeUpdateComponent,
    resolve: {
      foundationalDataType: FoundationalDataTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(foundationalDataTypeRoute)],
  exports: [RouterModule],
})
export class FoundationalDataTypeRoutingModule {}
