import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JobTemplateRateCardComponent } from '../list/job-template-rate-card.component';
import { JobTemplateRateCardDetailComponent } from '../detail/job-template-rate-card-detail.component';
import { JobTemplateRateCardUpdateComponent } from '../update/job-template-rate-card-update.component';
import { JobTemplateRateCardRoutingResolveService } from './job-template-rate-card-routing-resolve.service';

const jobTemplateRateCardRoute: Routes = [
  {
    path: '',
    component: JobTemplateRateCardComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobTemplateRateCardDetailComponent,
    resolve: {
      jobTemplateRateCard: JobTemplateRateCardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobTemplateRateCardUpdateComponent,
    resolve: {
      jobTemplateRateCard: JobTemplateRateCardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobTemplateRateCardUpdateComponent,
    resolve: {
      jobTemplateRateCard: JobTemplateRateCardRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(jobTemplateRateCardRoute)],
  exports: [RouterModule],
})
export class JobTemplateRateCardRoutingModule {}
