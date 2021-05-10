import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramUser, getProgramUserIdentifier } from '../program-user.model';

export type EntityResponseType = HttpResponse<IProgramUser>;
export type EntityArrayResponseType = HttpResponse<IProgramUser[]>;

@Injectable({ providedIn: 'root' })
export class ProgramUserService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/program-users');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(programUser: IProgramUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programUser);
    return this.http
      .post<IProgramUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(programUser: IProgramUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programUser);
    return this.http
      .put<IProgramUser>(`${this.resourceUrl}/${getProgramUserIdentifier(programUser) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(programUser: IProgramUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programUser);
    return this.http
      .patch<IProgramUser>(`${this.resourceUrl}/${getProgramUserIdentifier(programUser) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProgramUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProgramUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProgramUserToCollectionIfMissing(
    programUserCollection: IProgramUser[],
    ...programUsersToCheck: (IProgramUser | null | undefined)[]
  ): IProgramUser[] {
    const programUsers: IProgramUser[] = programUsersToCheck.filter(isPresent);
    if (programUsers.length > 0) {
      const programUserCollectionIdentifiers = programUserCollection.map(programUserItem => getProgramUserIdentifier(programUserItem)!);
      const programUsersToAdd = programUsers.filter(programUserItem => {
        const programUserIdentifier = getProgramUserIdentifier(programUserItem);
        if (programUserIdentifier == null || programUserCollectionIdentifiers.includes(programUserIdentifier)) {
          return false;
        }
        programUserCollectionIdentifiers.push(programUserIdentifier);
        return true;
      });
      return [...programUsersToAdd, ...programUserCollection];
    }
    return programUserCollection;
  }

  protected convertDateFromClient(programUser: IProgramUser): IProgramUser {
    return Object.assign({}, programUser, {
      createdAt: programUser.createdAt?.isValid() ? programUser.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: programUser.updatedAt?.isValid() ? programUser.updatedAt.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((programUser: IProgramUser) => {
        programUser.createdAt = programUser.createdAt ? dayjs(programUser.createdAt) : undefined;
        programUser.updatedAt = programUser.updatedAt ? dayjs(programUser.updatedAt) : undefined;
      });
    }
    return res;
  }
}
