import * as dayjs from 'dayjs';
import { IFoundationalData } from 'app/entities/foundational-data/foundational-data.model';
import { ICustomFieldType } from 'app/entities/custom-field-type/custom-field-type.model';

export interface ICustomFieldData {
  id?: number;
  value?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  foundationalData?: IFoundationalData | null;
  customFieldType?: ICustomFieldType | null;
}

export class CustomFieldData implements ICustomFieldData {
  constructor(
    public id?: number,
    public value?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public foundationalData?: IFoundationalData | null,
    public customFieldType?: ICustomFieldType | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getCustomFieldDataIdentifier(customFieldData: ICustomFieldData): number | undefined {
  return customFieldData.id;
}
