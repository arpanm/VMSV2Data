import * as dayjs from 'dayjs';
import { IJobTemplate } from 'app/entities/job-template/job-template.model';
import { IJobCategoryTitle } from 'app/entities/job-category-title/job-category-title.model';
import { Currency } from 'app/entities/enumerations/currency.model';

export interface IJobTemplateRateCard {
  id?: number;
  currency?: Currency | null;
  hourlyMin?: number | null;
  hourlyMax?: number | null;
  dailyMin?: number | null;
  dailyMax?: number | null;
  weeklyMin?: number | null;
  weeklyMax?: number | null;
  monthlyMin?: number | null;
  monthlyMax?: number | null;
  yearlyMin?: number | null;
  yearlyMax?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  template?: IJobTemplate | null;
  category?: IJobCategoryTitle | null;
}

export class JobTemplateRateCard implements IJobTemplateRateCard {
  constructor(
    public id?: number,
    public currency?: Currency | null,
    public hourlyMin?: number | null,
    public hourlyMax?: number | null,
    public dailyMin?: number | null,
    public dailyMax?: number | null,
    public weeklyMin?: number | null,
    public weeklyMax?: number | null,
    public monthlyMin?: number | null,
    public monthlyMax?: number | null,
    public yearlyMin?: number | null,
    public yearlyMax?: number | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public template?: IJobTemplate | null,
    public category?: IJobCategoryTitle | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getJobTemplateRateCardIdentifier(jobTemplateRateCard: IJobTemplateRateCard): number | undefined {
  return jobTemplateRateCard.id;
}
