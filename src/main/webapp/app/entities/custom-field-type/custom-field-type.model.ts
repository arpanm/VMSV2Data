import * as dayjs from 'dayjs';
import { IFoundationalDataType } from 'app/entities/foundational-data-type/foundational-data-type.model';
import { CustomFieldDataType } from 'app/entities/enumerations/custom-field-data-type.model';

export interface ICustomFieldType {
  id?: number;
  name?: string | null;
  type?: CustomFieldDataType | null;
  description?: string | null;
  label?: string | null;
  placeholder?: string | null;
  isMandatory?: boolean | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  foundationalDataType?: IFoundationalDataType | null;
}

export class CustomFieldType implements ICustomFieldType {
  constructor(
    public id?: number,
    public name?: string | null,
    public type?: CustomFieldDataType | null,
    public description?: string | null,
    public label?: string | null,
    public placeholder?: string | null,
    public isMandatory?: boolean | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public foundationalDataType?: IFoundationalDataType | null
  ) {
    this.isMandatory = this.isMandatory ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getCustomFieldTypeIdentifier(customFieldType: ICustomFieldType): number | undefined {
  return customFieldType.id;
}
