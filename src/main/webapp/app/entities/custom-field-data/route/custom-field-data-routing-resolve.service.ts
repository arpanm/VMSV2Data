import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomFieldData, CustomFieldData } from '../custom-field-data.model';
import { CustomFieldDataService } from '../service/custom-field-data.service';

@Injectable({ providedIn: 'root' })
export class CustomFieldDataRoutingResolveService implements Resolve<ICustomFieldData> {
  constructor(protected service: CustomFieldDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomFieldData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customFieldData: HttpResponse<CustomFieldData>) => {
          if (customFieldData.body) {
            return of(customFieldData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomFieldData());
  }
}
