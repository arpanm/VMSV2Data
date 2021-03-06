import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgram, getProgramIdentifier } from '../program.model';

export type EntityResponseType = HttpResponse<IProgram>;
export type EntityArrayResponseType = HttpResponse<IProgram[]>;

@Injectable({ providedIn: 'root' })
export class ProgramService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/programs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(program: IProgram): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(program);
    return this.http
      .post<IProgram>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(program: IProgram): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(program);
    return this.http
      .put<IProgram>(`${this.resourceUrl}/${getProgramIdentifier(program) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(program: IProgram): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(program);
    return this.http
      .patch<IProgram>(`${this.resourceUrl}/${getProgramIdentifier(program) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProgram>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProgram[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProgramToCollectionIfMissing(programCollection: IProgram[], ...programsToCheck: (IProgram | null | undefined)[]): IProgram[] {
    const programs: IProgram[] = programsToCheck.filter(isPresent);
    if (programs.length > 0) {
      const programCollectionIdentifiers = programCollection.map(programItem => getProgramIdentifier(programItem)!);
      const programsToAdd = programs.filter(programItem => {
        const programIdentifier = getProgramIdentifier(programItem);
        if (programIdentifier == null || programCollectionIdentifiers.includes(programIdentifier)) {
          return false;
        }
        programCollectionIdentifiers.push(programIdentifier);
        return true;
      });
      return [...programsToAdd, ...programCollection];
    }
    return programCollection;
  }

  protected convertDateFromClient(program: IProgram): IProgram {
    return Object.assign({}, program, {
      createdAt: program.createdAt?.isValid() ? program.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: program.updatedAt?.isValid() ? program.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((program: IProgram) => {
        program.createdAt = program.createdAt ? dayjs(program.createdAt) : undefined;
        program.updatedAt = program.updatedAt ? dayjs(program.updatedAt) : undefined;
      });
    }
    return res;
  }
}
