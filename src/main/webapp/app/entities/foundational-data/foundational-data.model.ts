import * as dayjs from 'dayjs';
import { IFoundationalDataType } from 'app/entities/foundational-data-type/foundational-data-type.model';
import { IProgramUser } from 'app/entities/program-user/program-user.model';

export interface IFoundationalData {
  id?: number;
  name?: string | null;
  code?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  foundationalDataType?: IFoundationalDataType | null;
  managers?: IProgramUser[] | null;
}

export class FoundationalData implements IFoundationalData {
  constructor(
    public id?: number,
    public name?: string | null,
    public code?: string | null,
    public description?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public foundationalDataType?: IFoundationalDataType | null,
    public managers?: IProgramUser[] | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getFoundationalDataIdentifier(foundationalData: IFoundationalData): number | undefined {
  return foundationalData.id;
}
