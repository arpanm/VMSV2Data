import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJobTemplate, JobTemplate } from '../job-template.model';
import { JobTemplateService } from '../service/job-template.service';

@Injectable({ providedIn: 'root' })
export class JobTemplateRoutingResolveService implements Resolve<IJobTemplate> {
  constructor(protected service: JobTemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((jobTemplate: HttpResponse<JobTemplate>) => {
          if (jobTemplate.body) {
            return of(jobTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobTemplate());
  }
}
