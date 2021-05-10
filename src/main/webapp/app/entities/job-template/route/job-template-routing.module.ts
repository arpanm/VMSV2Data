import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JobTemplateComponent } from '../list/job-template.component';
import { JobTemplateDetailComponent } from '../detail/job-template-detail.component';
import { JobTemplateUpdateComponent } from '../update/job-template-update.component';
import { JobTemplateRoutingResolveService } from './job-template-routing-resolve.service';

const jobTemplateRoute: Routes = [
  {
    path: '',
    component: JobTemplateComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobTemplateDetailComponent,
    resolve: {
      jobTemplate: JobTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobTemplateUpdateComponent,
    resolve: {
      jobTemplate: JobTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobTemplateUpdateComponent,
    resolve: {
      jobTemplate: JobTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(jobTemplateRoute)],
  exports: [RouterModule],
})
export class JobTemplateRoutingModule {}
