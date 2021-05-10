import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JobCategoryTitleComponent } from '../list/job-category-title.component';
import { JobCategoryTitleDetailComponent } from '../detail/job-category-title-detail.component';
import { JobCategoryTitleUpdateComponent } from '../update/job-category-title-update.component';
import { JobCategoryTitleRoutingResolveService } from './job-category-title-routing-resolve.service';

const jobCategoryTitleRoute: Routes = [
  {
    path: '',
    component: JobCategoryTitleComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobCategoryTitleDetailComponent,
    resolve: {
      jobCategoryTitle: JobCategoryTitleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobCategoryTitleUpdateComponent,
    resolve: {
      jobCategoryTitle: JobCategoryTitleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobCategoryTitleUpdateComponent,
    resolve: {
      jobCategoryTitle: JobCategoryTitleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(jobCategoryTitleRoute)],
  exports: [RouterModule],
})
export class JobCategoryTitleRoutingModule {}
