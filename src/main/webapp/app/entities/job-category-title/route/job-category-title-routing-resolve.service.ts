import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJobCategoryTitle, JobCategoryTitle } from '../job-category-title.model';
import { JobCategoryTitleService } from '../service/job-category-title.service';

@Injectable({ providedIn: 'root' })
export class JobCategoryTitleRoutingResolveService implements Resolve<IJobCategoryTitle> {
  constructor(protected service: JobCategoryTitleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobCategoryTitle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((jobCategoryTitle: HttpResponse<JobCategoryTitle>) => {
          if (jobCategoryTitle.body) {
            return of(jobCategoryTitle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobCategoryTitle());
  }
}
