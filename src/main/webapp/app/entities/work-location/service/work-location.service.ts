import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkLocation, getWorkLocationIdentifier } from '../work-location.model';

export type EntityResponseType = HttpResponse<IWorkLocation>;
export type EntityArrayResponseType = HttpResponse<IWorkLocation[]>;

@Injectable({ providedIn: 'root' })
export class WorkLocationService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/work-locations');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(workLocation: IWorkLocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workLocation);
    return this.http
      .post<IWorkLocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workLocation: IWorkLocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workLocation);
    return this.http
      .put<IWorkLocation>(`${this.resourceUrl}/${getWorkLocationIdentifier(workLocation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(workLocation: IWorkLocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workLocation);
    return this.http
      .patch<IWorkLocation>(`${this.resourceUrl}/${getWorkLocationIdentifier(workLocation) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkLocation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWorkLocationToCollectionIfMissing(
    workLocationCollection: IWorkLocation[],
    ...workLocationsToCheck: (IWorkLocation | null | undefined)[]
  ): IWorkLocation[] {
    const workLocations: IWorkLocation[] = workLocationsToCheck.filter(isPresent);
    if (workLocations.length > 0) {
      const workLocationCollectionIdentifiers = workLocationCollection.map(
        workLocationItem => getWorkLocationIdentifier(workLocationItem)!
      );
      const workLocationsToAdd = workLocations.filter(workLocationItem => {
        const workLocationIdentifier = getWorkLocationIdentifier(workLocationItem);
        if (workLocationIdentifier == null || workLocationCollectionIdentifiers.includes(workLocationIdentifier)) {
          return false;
        }
        workLocationCollectionIdentifiers.push(workLocationIdentifier);
        return true;
      });
      return [...workLocationsToAdd, ...workLocationCollection];
    }
    return workLocationCollection;
  }

  protected convertDateFromClient(workLocation: IWorkLocation): IWorkLocation {
    return Object.assign({}, workLocation, {
      createdAt: workLocation.createdAt?.isValid() ? workLocation.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: workLocation.updatedAt?.isValid() ? workLocation.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((workLocation: IWorkLocation) => {
        workLocation.createdAt = workLocation.createdAt ? dayjs(workLocation.createdAt) : undefined;
        workLocation.updatedAt = workLocation.updatedAt ? dayjs(workLocation.updatedAt) : undefined;
      });
    }
    return res;
  }
}
