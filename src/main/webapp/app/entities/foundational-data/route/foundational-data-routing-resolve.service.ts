import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFoundationalData, FoundationalData } from '../foundational-data.model';
import { FoundationalDataService } from '../service/foundational-data.service';

@Injectable({ providedIn: 'root' })
export class FoundationalDataRoutingResolveService implements Resolve<IFoundationalData> {
  constructor(protected service: FoundationalDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoundationalData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((foundationalData: HttpResponse<FoundationalData>) => {
          if (foundationalData.body) {
            return of(foundationalData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FoundationalData());
  }
}
