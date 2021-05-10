import * as dayjs from 'dayjs';

export interface IJobCategoryTitle {
  id?: number;
  category?: string | null;
  title?: string | null;
  code?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
}

export class JobCategoryTitle implements IJobCategoryTitle {
  constructor(
    public id?: number,
    public category?: string | null,
    public title?: string | null,
    public code?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getJobCategoryTitleIdentifier(jobCategoryTitle: IJobCategoryTitle): number | undefined {
  return jobCategoryTitle.id;
}
