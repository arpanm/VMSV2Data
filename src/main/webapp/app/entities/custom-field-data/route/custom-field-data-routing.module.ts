import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomFieldDataComponent } from '../list/custom-field-data.component';
import { CustomFieldDataDetailComponent } from '../detail/custom-field-data-detail.component';
import { CustomFieldDataUpdateComponent } from '../update/custom-field-data-update.component';
import { CustomFieldDataRoutingResolveService } from './custom-field-data-routing-resolve.service';

const customFieldDataRoute: Routes = [
  {
    path: '',
    component: CustomFieldDataComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomFieldDataDetailComponent,
    resolve: {
      customFieldData: CustomFieldDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomFieldDataUpdateComponent,
    resolve: {
      customFieldData: CustomFieldDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomFieldDataUpdateComponent,
    resolve: {
      customFieldData: CustomFieldDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customFieldDataRoute)],
  exports: [RouterModule],
})
export class CustomFieldDataRoutingModule {}
