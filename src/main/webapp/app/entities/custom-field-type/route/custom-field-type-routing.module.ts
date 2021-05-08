import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomFieldTypeComponent } from '../list/custom-field-type.component';
import { CustomFieldTypeDetailComponent } from '../detail/custom-field-type-detail.component';
import { CustomFieldTypeUpdateComponent } from '../update/custom-field-type-update.component';
import { CustomFieldTypeRoutingResolveService } from './custom-field-type-routing-resolve.service';

const customFieldTypeRoute: Routes = [
  {
    path: '',
    component: CustomFieldTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomFieldTypeDetailComponent,
    resolve: {
      customFieldType: CustomFieldTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomFieldTypeUpdateComponent,
    resolve: {
      customFieldType: CustomFieldTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomFieldTypeUpdateComponent,
    resolve: {
      customFieldType: CustomFieldTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customFieldTypeRoute)],
  exports: [RouterModule],
})
export class CustomFieldTypeRoutingModule {}
