import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HierarchyComponent } from '../list/hierarchy.component';
import { HierarchyDetailComponent } from '../detail/hierarchy-detail.component';
import { HierarchyUpdateComponent } from '../update/hierarchy-update.component';
import { HierarchyRoutingResolveService } from './hierarchy-routing-resolve.service';

const hierarchyRoute: Routes = [
  {
    path: '',
    component: HierarchyComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HierarchyDetailComponent,
    resolve: {
      hierarchy: HierarchyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HierarchyUpdateComponent,
    resolve: {
      hierarchy: HierarchyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HierarchyUpdateComponent,
    resolve: {
      hierarchy: HierarchyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hierarchyRoute)],
  exports: [RouterModule],
})
export class HierarchyRoutingModule {}
