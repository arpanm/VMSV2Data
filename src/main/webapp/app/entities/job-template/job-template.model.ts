import * as dayjs from 'dayjs';
import { IJobCategoryTitle } from 'app/entities/job-category-title/job-category-title.model';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';
import { JobTemplateDistributionType } from 'app/entities/enumerations/job-template-distribution-type.model';

export interface IJobTemplate {
  id?: number;
  templateTitle?: string | null;
  description?: string | null;
  jobLevel?: number | null;
  isDescriptionEditable?: boolean | null;
  distributionType?: JobTemplateDistributionType | null;
  distributionLimit?: number | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  category?: IJobCategoryTitle | null;
  hierarchy?: IHierarchy | null;
}

export class JobTemplate implements IJobTemplate {
  constructor(
    public id?: number,
    public templateTitle?: string | null,
    public description?: string | null,
    public jobLevel?: number | null,
    public isDescriptionEditable?: boolean | null,
    public distributionType?: JobTemplateDistributionType | null,
    public distributionLimit?: number | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public category?: IJobCategoryTitle | null,
    public hierarchy?: IHierarchy | null
  ) {
    this.isDescriptionEditable = this.isDescriptionEditable ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getJobTemplateIdentifier(jobTemplate: IJobTemplate): number | undefined {
  return jobTemplate.id;
}
