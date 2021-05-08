import * as dayjs from 'dayjs';
import { IProgram } from 'app/entities/program/program.model';
import { IProgramUser } from 'app/entities/program-user/program-user.model';
import { Language } from 'app/entities/enumerations/language.model';
import { Currency } from 'app/entities/enumerations/currency.model';
import { Country } from 'app/entities/enumerations/country.model';

export interface IHierarchy {
  id?: number;
  name?: string | null;
  workFlow?: string | null;
  preferredLanguage?: Language | null;
  preferredCurrency?: Currency | null;
  primaryCountry?: Country | null;
  primaryAddress?: string | null;
  secondaryCountry?: Country | null;
  secondaryAddress?: string | null;
  primaryContactName?: string | null;
  primaryContactDesignation?: string | null;
  primaryContactEmail?: string | null;
  primaryContactPhone?: string | null;
  secondaryContactName?: string | null;
  secondaryContactDesignation?: string | null;
  secondaryContactEmail?: string | null;
  secondaryContactPhone?: string | null;
  fileUploadPath?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  client?: IProgram | null;
  parent?: IHierarchy | null;
  managers?: IProgramUser[] | null;
}

export class Hierarchy implements IHierarchy {
  constructor(
    public id?: number,
    public name?: string | null,
    public workFlow?: string | null,
    public preferredLanguage?: Language | null,
    public preferredCurrency?: Currency | null,
    public primaryCountry?: Country | null,
    public primaryAddress?: string | null,
    public secondaryCountry?: Country | null,
    public secondaryAddress?: string | null,
    public primaryContactName?: string | null,
    public primaryContactDesignation?: string | null,
    public primaryContactEmail?: string | null,
    public primaryContactPhone?: string | null,
    public secondaryContactName?: string | null,
    public secondaryContactDesignation?: string | null,
    public secondaryContactEmail?: string | null,
    public secondaryContactPhone?: string | null,
    public fileUploadPath?: string | null,
    public isActive?: boolean | null,
    public createdBy?: string | null,
    public createdAt?: dayjs.Dayjs | null,
    public updatedBy?: string | null,
    public updatedAt?: dayjs.Dayjs | null,
    public client?: IProgram | null,
    public parent?: IHierarchy | null,
    public managers?: IProgramUser[] | null
  ) {
    this.isActive = this.isActive ?? false;
  }
}

export function getHierarchyIdentifier(hierarchy: IHierarchy): number | undefined {
  return hierarchy.id;
}
