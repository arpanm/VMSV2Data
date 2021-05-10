import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomFieldType, getCustomFieldTypeIdentifier } from '../custom-field-type.model';

export type EntityResponseType = HttpResponse<ICustomFieldType>;
export type EntityArrayResponseType = HttpResponse<ICustomFieldType[]>;

@Injectable({ providedIn: 'root' })
export class CustomFieldTypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/custom-field-types');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(customFieldType: ICustomFieldType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customFieldType);
    return this.http
      .post<ICustomFieldType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customFieldType: ICustomFieldType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customFieldType);
    return this.http
      .put<ICustomFieldType>(`${this.resourceUrl}/${getCustomFieldTypeIdentifier(customFieldType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(customFieldType: ICustomFieldType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customFieldType);
    return this.http
      .patch<ICustomFieldType>(`${this.resourceUrl}/${getCustomFieldTypeIdentifier(customFieldType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomFieldType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomFieldType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomFieldTypeToCollectionIfMissing(
    customFieldTypeCollection: ICustomFieldType[],
    ...customFieldTypesToCheck: (ICustomFieldType | null | undefined)[]
  ): ICustomFieldType[] {
    const customFieldTypes: ICustomFieldType[] = customFieldTypesToCheck.filter(isPresent);
    if (customFieldTypes.length > 0) {
      const customFieldTypeCollectionIdentifiers = customFieldTypeCollection.map(
        customFieldTypeItem => getCustomFieldTypeIdentifier(customFieldTypeItem)!
      );
      const customFieldTypesToAdd = customFieldTypes.filter(customFieldTypeItem => {
        const customFieldTypeIdentifier = getCustomFieldTypeIdentifier(customFieldTypeItem);
        if (customFieldTypeIdentifier == null || customFieldTypeCollectionIdentifiers.includes(customFieldTypeIdentifier)) {
          return false;
        }
        customFieldTypeCollectionIdentifiers.push(customFieldTypeIdentifier);
        return true;
      });
      return [...customFieldTypesToAdd, ...customFieldTypeCollection];
    }
    return customFieldTypeCollection;
  }

  protected convertDateFromClient(customFieldType: ICustomFieldType): ICustomFieldType {
    return Object.assign({}, customFieldType, {
      createdAt: customFieldType.createdAt?.isValid() ? customFieldType.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: customFieldType.updatedAt?.isValid() ? customFieldType.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((customFieldType: ICustomFieldType) => {
        customFieldType.createdAt = customFieldType.createdAt ? dayjs(customFieldType.createdAt) : undefined;
        customFieldType.updatedAt = customFieldType.updatedAt ? dayjs(customFieldType.updatedAt) : undefined;
      });
    }
    return res;
  }
}
