import * as dayjs from 'dayjs';
import { IWorkLocation } from 'app/entities/work-location/work-location.model';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';
import { IFoundationalData } from 'app/entities/foundational-data/foundational-data.model';
import { Role } from 'app/entities/enumerations/role.model';

export interface IProgramUser {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  sourceId?: string | null;
  phoneNumber?: string | null;
  clientDesignation?: string | null;
  simplifyRole?: Role | null;
  fileUploadPath?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  manager?: IProgramUser | null;
  location?: IWorkLocation | null;
  hierarchies?: IHierarchy[] | null;
  foundationalData?: IFoundationalData[] | null;
}

export class ProgramUser implements IProgramUser {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public sourceId?: string | null,
    public phoneNumber?: string | null,
    public clientDesignation?: string | null,
    public simplifyRole?: Role | null,
    public fileUploadPath?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public manager?: IProgramUser | null,
    public location?: IWorkLocation | null,
    public hierarchies?: IHierarchy[] | null,
    public foundationalData?: IFoundationalData[] | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getProgramUserIdentifier(programUser: IProgramUser): number | undefined {
  return programUser.id;
}
