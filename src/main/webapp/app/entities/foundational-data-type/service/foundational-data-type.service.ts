import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFoundationalDataType, getFoundationalDataTypeIdentifier } from '../foundational-data-type.model';

export type EntityResponseType = HttpResponse<IFoundationalDataType>;
export type EntityArrayResponseType = HttpResponse<IFoundationalDataType[]>;

@Injectable({ providedIn: 'root' })
export class FoundationalDataTypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/foundational-data-types');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(foundationalDataType: IFoundationalDataType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foundationalDataType);
    return this.http
      .post<IFoundationalDataType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(foundationalDataType: IFoundationalDataType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foundationalDataType);
    return this.http
      .put<IFoundationalDataType>(`${this.resourceUrl}/${getFoundationalDataTypeIdentifier(foundationalDataType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(foundationalDataType: IFoundationalDataType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foundationalDataType);
    return this.http
      .patch<IFoundationalDataType>(`${this.resourceUrl}/${getFoundationalDataTypeIdentifier(foundationalDataType) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFoundationalDataType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFoundationalDataType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFoundationalDataTypeToCollectionIfMissing(
    foundationalDataTypeCollection: IFoundationalDataType[],
    ...foundationalDataTypesToCheck: (IFoundationalDataType | null | undefined)[]
  ): IFoundationalDataType[] {
    const foundationalDataTypes: IFoundationalDataType[] = foundationalDataTypesToCheck.filter(isPresent);
    if (foundationalDataTypes.length > 0) {
      const foundationalDataTypeCollectionIdentifiers = foundationalDataTypeCollection.map(
        foundationalDataTypeItem => getFoundationalDataTypeIdentifier(foundationalDataTypeItem)!
      );
      const foundationalDataTypesToAdd = foundationalDataTypes.filter(foundationalDataTypeItem => {
        const foundationalDataTypeIdentifier = getFoundationalDataTypeIdentifier(foundationalDataTypeItem);
        if (foundationalDataTypeIdentifier == null || foundationalDataTypeCollectionIdentifiers.includes(foundationalDataTypeIdentifier)) {
          return false;
        }
        foundationalDataTypeCollectionIdentifiers.push(foundationalDataTypeIdentifier);
        return true;
      });
      return [...foundationalDataTypesToAdd, ...foundationalDataTypeCollection];
    }
    return foundationalDataTypeCollection;
  }

  protected convertDateFromClient(foundationalDataType: IFoundationalDataType): IFoundationalDataType {
    return Object.assign({}, foundationalDataType, {
      createdAt: foundationalDataType.createdAt?.isValid() ? foundationalDataType.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: foundationalDataType.updatedAt?.isValid() ? foundationalDataType.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((foundationalDataType: IFoundationalDataType) => {
        foundationalDataType.createdAt = foundationalDataType.createdAt ? dayjs(foundationalDataType.createdAt) : undefined;
        foundationalDataType.updatedAt = foundationalDataType.updatedAt ? dayjs(foundationalDataType.updatedAt) : undefined;
      });
    }
    return res;
  }
}
