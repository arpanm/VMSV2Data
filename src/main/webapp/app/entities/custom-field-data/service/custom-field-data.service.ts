import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomFieldData, getCustomFieldDataIdentifier } from '../custom-field-data.model';

export type EntityResponseType = HttpResponse<ICustomFieldData>;
export type EntityArrayResponseType = HttpResponse<ICustomFieldData[]>;

@Injectable({ providedIn: 'root' })
export class CustomFieldDataService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/custom-field-data');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(customFieldData: ICustomFieldData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customFieldData);
    return this.http
      .post<ICustomFieldData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customFieldData: ICustomFieldData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customFieldData);
    return this.http
      .put<ICustomFieldData>(`${this.resourceUrl}/${getCustomFieldDataIdentifier(customFieldData) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(customFieldData: ICustomFieldData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customFieldData);
    return this.http
      .patch<ICustomFieldData>(`${this.resourceUrl}/${getCustomFieldDataIdentifier(customFieldData) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomFieldData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomFieldData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomFieldDataToCollectionIfMissing(
    customFieldDataCollection: ICustomFieldData[],
    ...customFieldDataToCheck: (ICustomFieldData | null | undefined)[]
  ): ICustomFieldData[] {
    const customFieldData: ICustomFieldData[] = customFieldDataToCheck.filter(isPresent);
    if (customFieldData.length > 0) {
      const customFieldDataCollectionIdentifiers = customFieldDataCollection.map(
        customFieldDataItem => getCustomFieldDataIdentifier(customFieldDataItem)!
      );
      const customFieldDataToAdd = customFieldData.filter(customFieldDataItem => {
        const customFieldDataIdentifier = getCustomFieldDataIdentifier(customFieldDataItem);
        if (customFieldDataIdentifier == null || customFieldDataCollectionIdentifiers.includes(customFieldDataIdentifier)) {
          return false;
        }
        customFieldDataCollectionIdentifiers.push(customFieldDataIdentifier);
        return true;
      });
      return [...customFieldDataToAdd, ...customFieldDataCollection];
    }
    return customFieldDataCollection;
  }

  protected convertDateFromClient(customFieldData: ICustomFieldData): ICustomFieldData {
    return Object.assign({}, customFieldData, {
      createdAt: customFieldData.createdAt?.isValid() ? customFieldData.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: customFieldData.updatedAt?.isValid() ? customFieldData.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((customFieldData: ICustomFieldData) => {
        customFieldData.createdAt = customFieldData.createdAt ? dayjs(customFieldData.createdAt) : undefined;
        customFieldData.updatedAt = customFieldData.updatedAt ? dayjs(customFieldData.updatedAt) : undefined;
      });
    }
    return res;
  }
}
