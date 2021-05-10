import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomFieldType, CustomFieldType } from '../custom-field-type.model';
import { CustomFieldTypeService } from '../service/custom-field-type.service';

@Injectable({ providedIn: 'root' })
export class CustomFieldTypeRoutingResolveService implements Resolve<ICustomFieldType> {
  constructor(protected service: CustomFieldTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomFieldType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customFieldType: HttpResponse<CustomFieldType>) => {
          if (customFieldType.body) {
            return of(customFieldType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomFieldType());
  }
}
