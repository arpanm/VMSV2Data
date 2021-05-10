import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFoundationalDataType, FoundationalDataType } from '../foundational-data-type.model';
import { FoundationalDataTypeService } from '../service/foundational-data-type.service';

@Injectable({ providedIn: 'root' })
export class FoundationalDataTypeRoutingResolveService implements Resolve<IFoundationalDataType> {
  constructor(protected service: FoundationalDataTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoundationalDataType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((foundationalDataType: HttpResponse<FoundationalDataType>) => {
          if (foundationalDataType.body) {
            return of(foundationalDataType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FoundationalDataType());
  }
}
