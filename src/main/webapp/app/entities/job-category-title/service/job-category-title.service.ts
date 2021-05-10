import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJobCategoryTitle, getJobCategoryTitleIdentifier } from '../job-category-title.model';

export type EntityResponseType = HttpResponse<IJobCategoryTitle>;
export type EntityArrayResponseType = HttpResponse<IJobCategoryTitle[]>;

@Injectable({ providedIn: 'root' })
export class JobCategoryTitleService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/job-category-titles');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(jobCategoryTitle: IJobCategoryTitle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobCategoryTitle);
    return this.http
      .post<IJobCategoryTitle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobCategoryTitle: IJobCategoryTitle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobCategoryTitle);
    return this.http
      .put<IJobCategoryTitle>(`${this.resourceUrl}/${getJobCategoryTitleIdentifier(jobCategoryTitle) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(jobCategoryTitle: IJobCategoryTitle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobCategoryTitle);
    return this.http
      .patch<IJobCategoryTitle>(`${this.resourceUrl}/${getJobCategoryTitleIdentifier(jobCategoryTitle) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobCategoryTitle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobCategoryTitle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addJobCategoryTitleToCollectionIfMissing(
    jobCategoryTitleCollection: IJobCategoryTitle[],
    ...jobCategoryTitlesToCheck: (IJobCategoryTitle | null | undefined)[]
  ): IJobCategoryTitle[] {
    const jobCategoryTitles: IJobCategoryTitle[] = jobCategoryTitlesToCheck.filter(isPresent);
    if (jobCategoryTitles.length > 0) {
      const jobCategoryTitleCollectionIdentifiers = jobCategoryTitleCollection.map(
        jobCategoryTitleItem => getJobCategoryTitleIdentifier(jobCategoryTitleItem)!
      );
      const jobCategoryTitlesToAdd = jobCategoryTitles.filter(jobCategoryTitleItem => {
        const jobCategoryTitleIdentifier = getJobCategoryTitleIdentifier(jobCategoryTitleItem);
        if (jobCategoryTitleIdentifier == null || jobCategoryTitleCollectionIdentifiers.includes(jobCategoryTitleIdentifier)) {
          return false;
        }
        jobCategoryTitleCollectionIdentifiers.push(jobCategoryTitleIdentifier);
        return true;
      });
      return [...jobCategoryTitlesToAdd, ...jobCategoryTitleCollection];
    }
    return jobCategoryTitleCollection;
  }

  protected convertDateFromClient(jobCategoryTitle: IJobCategoryTitle): IJobCategoryTitle {
    return Object.assign({}, jobCategoryTitle, {
      createdAt: jobCategoryTitle.createdAt?.isValid() ? jobCategoryTitle.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: jobCategoryTitle.updatedAt?.isValid() ? jobCategoryTitle.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((jobCategoryTitle: IJobCategoryTitle) => {
        jobCategoryTitle.createdAt = jobCategoryTitle.createdAt ? dayjs(jobCategoryTitle.createdAt) : undefined;
        jobCategoryTitle.updatedAt = jobCategoryTitle.updatedAt ? dayjs(jobCategoryTitle.updatedAt) : undefined;
      });
    }
    return res;
  }
}
