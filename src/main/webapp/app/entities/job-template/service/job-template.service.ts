import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJobTemplate, getJobTemplateIdentifier } from '../job-template.model';

export type EntityResponseType = HttpResponse<IJobTemplate>;
export type EntityArrayResponseType = HttpResponse<IJobTemplate[]>;

@Injectable({ providedIn: 'root' })
export class JobTemplateService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/job-templates');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(jobTemplate: IJobTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobTemplate);
    return this.http
      .post<IJobTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobTemplate: IJobTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobTemplate);
    return this.http
      .put<IJobTemplate>(`${this.resourceUrl}/${getJobTemplateIdentifier(jobTemplate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(jobTemplate: IJobTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobTemplate);
    return this.http
      .patch<IJobTemplate>(`${this.resourceUrl}/${getJobTemplateIdentifier(jobTemplate) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addJobTemplateToCollectionIfMissing(
    jobTemplateCollection: IJobTemplate[],
    ...jobTemplatesToCheck: (IJobTemplate | null | undefined)[]
  ): IJobTemplate[] {
    const jobTemplates: IJobTemplate[] = jobTemplatesToCheck.filter(isPresent);
    if (jobTemplates.length > 0) {
      const jobTemplateCollectionIdentifiers = jobTemplateCollection.map(jobTemplateItem => getJobTemplateIdentifier(jobTemplateItem)!);
      const jobTemplatesToAdd = jobTemplates.filter(jobTemplateItem => {
        const jobTemplateIdentifier = getJobTemplateIdentifier(jobTemplateItem);
        if (jobTemplateIdentifier == null || jobTemplateCollectionIdentifiers.includes(jobTemplateIdentifier)) {
          return false;
        }
        jobTemplateCollectionIdentifiers.push(jobTemplateIdentifier);
        return true;
      });
      return [...jobTemplatesToAdd, ...jobTemplateCollection];
    }
    return jobTemplateCollection;
  }

  protected convertDateFromClient(jobTemplate: IJobTemplate): IJobTemplate {
    return Object.assign({}, jobTemplate, {
      createdAt: jobTemplate.createdAt?.isValid() ? jobTemplate.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: jobTemplate.updatedAt?.isValid() ? jobTemplate.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((jobTemplate: IJobTemplate) => {
        jobTemplate.createdAt = jobTemplate.createdAt ? dayjs(jobTemplate.createdAt) : undefined;
        jobTemplate.updatedAt = jobTemplate.updatedAt ? dayjs(jobTemplate.updatedAt) : undefined;
      });
    }
    return res;
  }
}
