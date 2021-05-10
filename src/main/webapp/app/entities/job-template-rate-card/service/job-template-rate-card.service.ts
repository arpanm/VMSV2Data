import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJobTemplateRateCard, getJobTemplateRateCardIdentifier } from '../job-template-rate-card.model';

export type EntityResponseType = HttpResponse<IJobTemplateRateCard>;
export type EntityArrayResponseType = HttpResponse<IJobTemplateRateCard[]>;

@Injectable({ providedIn: 'root' })
export class JobTemplateRateCardService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/job-template-rate-cards');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(jobTemplateRateCard: IJobTemplateRateCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobTemplateRateCard);
    return this.http
      .post<IJobTemplateRateCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobTemplateRateCard: IJobTemplateRateCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobTemplateRateCard);
    return this.http
      .put<IJobTemplateRateCard>(`${this.resourceUrl}/${getJobTemplateRateCardIdentifier(jobTemplateRateCard) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(jobTemplateRateCard: IJobTemplateRateCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobTemplateRateCard);
    return this.http
      .patch<IJobTemplateRateCard>(`${this.resourceUrl}/${getJobTemplateRateCardIdentifier(jobTemplateRateCard) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobTemplateRateCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobTemplateRateCard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addJobTemplateRateCardToCollectionIfMissing(
    jobTemplateRateCardCollection: IJobTemplateRateCard[],
    ...jobTemplateRateCardsToCheck: (IJobTemplateRateCard | null | undefined)[]
  ): IJobTemplateRateCard[] {
    const jobTemplateRateCards: IJobTemplateRateCard[] = jobTemplateRateCardsToCheck.filter(isPresent);
    if (jobTemplateRateCards.length > 0) {
      const jobTemplateRateCardCollectionIdentifiers = jobTemplateRateCardCollection.map(
        jobTemplateRateCardItem => getJobTemplateRateCardIdentifier(jobTemplateRateCardItem)!
      );
      const jobTemplateRateCardsToAdd = jobTemplateRateCards.filter(jobTemplateRateCardItem => {
        const jobTemplateRateCardIdentifier = getJobTemplateRateCardIdentifier(jobTemplateRateCardItem);
        if (jobTemplateRateCardIdentifier == null || jobTemplateRateCardCollectionIdentifiers.includes(jobTemplateRateCardIdentifier)) {
          return false;
        }
        jobTemplateRateCardCollectionIdentifiers.push(jobTemplateRateCardIdentifier);
        return true;
      });
      return [...jobTemplateRateCardsToAdd, ...jobTemplateRateCardCollection];
    }
    return jobTemplateRateCardCollection;
  }

  protected convertDateFromClient(jobTemplateRateCard: IJobTemplateRateCard): IJobTemplateRateCard {
    return Object.assign({}, jobTemplateRateCard, {
      createdAt: jobTemplateRateCard.createdAt?.isValid() ? jobTemplateRateCard.createdAt.format(DATE_FORMAT) : undefined,
      updatedAt: jobTemplateRateCard.updatedAt?.isValid() ? jobTemplateRateCard.updatedAt.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((jobTemplateRateCard: IJobTemplateRateCard) => {
        jobTemplateRateCard.createdAt = jobTemplateRateCard.createdAt ? dayjs(jobTemplateRateCard.createdAt) : undefined;
        jobTemplateRateCard.updatedAt = jobTemplateRateCard.updatedAt ? dayjs(jobTemplateRateCard.updatedAt) : undefined;
      });
    }
    return res;
  }
}
