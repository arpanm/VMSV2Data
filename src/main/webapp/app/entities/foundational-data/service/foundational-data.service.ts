import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFoundationalData, getFoundationalDataIdentifier } from '../foundational-data.model';

export type EntityResponseType = HttpResponse<IFoundationalData>;
export type EntityArrayResponseType = HttpResponse<IFoundationalData[]>;

@Injectable({ providedIn: 'root' })
export class FoundationalDataService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/foundational-data');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(foundationalData: IFoundationalData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foundationalData);
    return this.http
      .post<IFoundationalData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(foundationalData: IFoundationalData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foundationalData);
    return this.http
      .put<IFoundationalData>(`${this.resourceUrl}/${getFoundationalDataIdentifier(foundationalData) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(foundationalData: IFoundationalData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foundationalData);
    return this.http
      .patch<IFoundationalData>(`${this.resourceUrl}/${getFoundationalDataIdentifier(foundationalData) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFoundationalData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFoundationalData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFoundationalDataToCollectionIfMissing(
    foundationalDataCollection: IFoundationalData[],
    ...foundationalDataToCheck: (IFoundationalData | null | undefined)[]
  ): IFoundationalData[] {
    const foundationalData: IFoundationalData[] = foundationalDataToCheck.filter(isPresent);
    if (foundationalData.length > 0) {
      const foundationalDataCollectionIdentifiers = foundationalDataCollection.map(
        foundationalDataItem => getFoundationalDataIdentifier(foundationalDataItem)!
      );
      const foundationalDataToAdd = foundationalData.filter(foundationalDataItem => {
        const foundationalDataIdentifier = getFoundationalDataIdentifier(foundationalDataItem);
        if (foundationalDataIdentifier == null || foundationalDataCollectionIdentifiers.includes(foundationalDataIdentifier)) {
          return false;
        }
        foundationalDataCollectionIdentifiers.push(foundationalDataIdentifier);
        return true;
      });
      return [...foundationalDataToAdd, ...foundationalDataCollection];
    }
    return foundationalDataCollection;
  }

  protected convertDateFromClient(foundationalData: IFoundationalData): IFoundationalData {
    return Object.assign({}, foundationalData, {
      createdAt: foundationalData.createdAt?.isValid() ? foundationalData.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: foundationalData.updatedAt?.isValid() ? foundationalData.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((foundationalData: IFoundationalData) => {
        foundationalData.createdAt = foundationalData.createdAt ? dayjs(foundationalData.createdAt) : undefined;
        foundationalData.updatedAt = foundationalData.updatedAt ? dayjs(foundationalData.updatedAt) : undefined;
      });
    }
    return res;
  }
}
