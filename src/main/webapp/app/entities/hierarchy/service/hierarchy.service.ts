import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHierarchy, getHierarchyIdentifier } from '../hierarchy.model';

export type EntityResponseType = HttpResponse<IHierarchy>;
export type EntityArrayResponseType = HttpResponse<IHierarchy[]>;

@Injectable({ providedIn: 'root' })
export class HierarchyService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/hierarchies');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(hierarchy: IHierarchy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hierarchy);
    return this.http
      .post<IHierarchy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hierarchy: IHierarchy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hierarchy);
    return this.http
      .put<IHierarchy>(`${this.resourceUrl}/${getHierarchyIdentifier(hierarchy) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hierarchy: IHierarchy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hierarchy);
    return this.http
      .patch<IHierarchy>(`${this.resourceUrl}/${getHierarchyIdentifier(hierarchy) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHierarchy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHierarchy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHierarchyToCollectionIfMissing(
    hierarchyCollection: IHierarchy[],
    ...hierarchiesToCheck: (IHierarchy | null | undefined)[]
  ): IHierarchy[] {
    const hierarchies: IHierarchy[] = hierarchiesToCheck.filter(isPresent);
    if (hierarchies.length > 0) {
      const hierarchyCollectionIdentifiers = hierarchyCollection.map(hierarchyItem => getHierarchyIdentifier(hierarchyItem)!);
      const hierarchiesToAdd = hierarchies.filter(hierarchyItem => {
        const hierarchyIdentifier = getHierarchyIdentifier(hierarchyItem);
        if (hierarchyIdentifier == null || hierarchyCollectionIdentifiers.includes(hierarchyIdentifier)) {
          return false;
        }
        hierarchyCollectionIdentifiers.push(hierarchyIdentifier);
        return true;
      });
      return [...hierarchiesToAdd, ...hierarchyCollection];
    }
    return hierarchyCollection;
  }

  protected convertDateFromClient(hierarchy: IHierarchy): IHierarchy {
    return Object.assign({}, hierarchy, {
      createdAt: hierarchy.createdAt?.isValid() ? hierarchy.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: hierarchy.updatedAt?.isValid() ? hierarchy.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((hierarchy: IHierarchy) => {
        hierarchy.createdAt = hierarchy.createdAt ? dayjs(hierarchy.createdAt) : undefined;
        hierarchy.updatedAt = hierarchy.updatedAt ? dayjs(hierarchy.updatedAt) : undefined;
      });
    }
    return res;
  }
}
