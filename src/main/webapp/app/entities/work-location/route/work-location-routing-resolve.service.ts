import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkLocation, WorkLocation } from '../work-location.model';
import { WorkLocationService } from '../service/work-location.service';

@Injectable({ providedIn: 'root' })
export class WorkLocationRoutingResolveService implements Resolve<IWorkLocation> {
  constructor(protected service: WorkLocationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((workLocation: HttpResponse<WorkLocation>) => {
          if (workLocation.body) {
            return of(workLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkLocation());
  }
}
