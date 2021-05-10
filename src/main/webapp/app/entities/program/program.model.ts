import * as dayjs from 'dayjs';
import { Region } from 'app/entities/enumerations/region.model';

export interface IProgram {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  deploymentRegion?: Region | null;
  subdomain?: string | null;
  implementationManagerEmail?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
}

export class Program implements IProgram {
  constructor(
    public id?: number,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public deploymentRegion?: Region | null,
    public subdomain?: string | null,
    public implementationManagerEmail?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getProgramIdentifier(program: IProgram): number | undefined {
  return program.id;
}
