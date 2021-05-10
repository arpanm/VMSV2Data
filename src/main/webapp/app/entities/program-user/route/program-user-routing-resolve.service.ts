import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramUser, ProgramUser } from '../program-user.model';
import { ProgramUserService } from '../service/program-user.service';

@Injectable({ providedIn: 'root' })
export class ProgramUserRoutingResolveService implements Resolve<IProgramUser> {
  constructor(protected service: ProgramUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programUser: HttpResponse<ProgramUser>) => {
          if (programUser.body) {
            return of(programUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProgramUser());
  }
}
