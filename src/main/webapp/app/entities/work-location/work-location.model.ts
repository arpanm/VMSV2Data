import * as dayjs from 'dayjs';
import { IProgram } from 'app/entities/program/program.model';
import { Country } from 'app/entities/enumerations/country.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IWorkLocation {
  id?: number;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  country?: Country | null;
  state?: State | null;
  address?: string | null;
  fileUploadPath?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  client?: IProgram | null;
}

export class WorkLocation implements IWorkLocation {
  constructor(
    public id?: number,
    public code?: string | null,
    public name?: string | null,
    public description?: string | null,
    public country?: Country | null,
    public state?: State | null,
    public address?: string | null,
    public fileUploadPath?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public client?: IProgram | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getWorkLocationIdentifier(workLocation: IWorkLocation): number | undefined {
  return workLocation.id;
}
