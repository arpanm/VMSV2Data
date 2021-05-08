import * as dayjs from 'dayjs';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';

export interface IFoundationalDataType {
  id?: number;
  name?: string | null;
  description?: string | null;
  requiredInHierarchy?: boolean | null;
  requiredInJobs?: boolean | null;
  requiredInSow?: boolean | null;
  fileUploadPath?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  hierarchy?: IHierarchy | null;
}

export class FoundationalDataType implements IFoundationalDataType {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public requiredInHierarchy?: boolean | null,
    public requiredInJobs?: boolean | null,
    public requiredInSow?: boolean | null,
    public fileUploadPath?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public hierarchy?: IHierarchy | null
  ) {
    this.requiredInHierarchy = this.requiredInHierarchy ?? false;
    this.requiredInJobs = this.requiredInJobs ?? false;
    this.requiredInSow = this.requiredInSow ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getFoundationalDataTypeIdentifier(foundationalDataType: IFoundationalDataType): number | undefined {
  return foundationalDataType.id;
}
