import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJobTemplateRateCard, JobTemplateRateCard } from '../job-template-rate-card.model';
import { JobTemplateRateCardService } from '../service/job-template-rate-card.service';

@Injectable({ providedIn: 'root' })
export class JobTemplateRateCardRoutingResolveService implements Resolve<IJobTemplateRateCard> {
  constructor(protected service: JobTemplateRateCardService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobTemplateRateCard> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((jobTemplateRateCard: HttpResponse<JobTemplateRateCard>) => {
          if (jobTemplateRateCard.body) {
            return of(jobTemplateRateCard.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobTemplateRateCard());
  }
}
