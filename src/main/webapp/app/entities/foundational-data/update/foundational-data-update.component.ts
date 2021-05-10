import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFoundationalData, FoundationalData } from '../foundational-data.model';
import { FoundationalDataService } from '../service/foundational-data.service';
import { IFoundationalDataType } from 'app/entities/foundational-data-type/foundational-data-type.model';
import { FoundationalDataTypeService } from 'app/entities/foundational-data-type/service/foundational-data-type.service';
import { IProgramUser } from 'app/entities/program-user/program-user.model';
import { ProgramUserService } from 'app/entities/program-user/service/program-user.service';

@Component({
  selector: 'jhi-foundational-data-update',
  templateUrl: './foundational-data-update.component.html',
})
export class FoundationalDataUpdateComponent implements OnInit {
  isSaving = false;

  foundationalDataTypesSharedCollection: IFoundationalDataType[] = [];
  programUsersSharedCollection: IProgramUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    code: [],
    description: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    foundationalDataType: [],
    managers: [],
  });

  constructor(
    protected foundationalDataService: FoundationalDataService,
    protected foundationalDataTypeService: FoundationalDataTypeService,
    protected programUserService: ProgramUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foundationalData }) => {
      this.updateForm(foundationalData);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const foundationalData = this.createFromForm();
    if (foundationalData.id !== undefined) {
      this.subscribeToSaveResponse(this.foundationalDataService.update(foundationalData));
    } else {
      this.subscribeToSaveResponse(this.foundationalDataService.create(foundationalData));
    }
  }

  trackFoundationalDataTypeById(index: number, item: IFoundationalDataType): number {
    return item.id!;
  }

  trackProgramUserById(index: number, item: IProgramUser): number {
    return item.id!;
  }

  getSelectedProgramUser(option: IProgramUser, selectedVals?: IProgramUser[]): IProgramUser {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoundationalData>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(foundationalData: IFoundationalData): void {
    this.editForm.patchValue({
      id: foundationalData.id,
      name: foundationalData.name,
      code: foundationalData.code,
      description: foundationalData.description,
      isActive: foundationalData.isActive,
      createdBy: foundationalData.createdBy,
      createdAt: foundationalData.createdAt,
      updatedBy: foundationalData.updatedBy,
      updatedAt: foundationalData.updatedAt,
      foundationalDataType: foundationalData.foundationalDataType,
      managers: foundationalData.managers,
    });

    this.foundationalDataTypesSharedCollection = this.foundationalDataTypeService.addFoundationalDataTypeToCollectionIfMissing(
      this.foundationalDataTypesSharedCollection,
      foundationalData.foundationalDataType
    );
    this.programUsersSharedCollection = this.programUserService.addProgramUserToCollectionIfMissing(
      this.programUsersSharedCollection,
      ...(foundationalData.managers ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.foundationalDataTypeService
      .query()
      .pipe(map((res: HttpResponse<IFoundationalDataType[]>) => res.body ?? []))
      .pipe(
        map((foundationalDataTypes: IFoundationalDataType[]) =>
          this.foundationalDataTypeService.addFoundationalDataTypeToCollectionIfMissing(
            foundationalDataTypes,
            this.editForm.get('foundationalDataType')!.value
          )
        )
      )
      .subscribe((foundationalDataTypes: IFoundationalDataType[]) => (this.foundationalDataTypesSharedCollection = foundationalDataTypes));

    this.programUserService
      .query()
      .pipe(map((res: HttpResponse<IProgramUser[]>) => res.body ?? []))
      .pipe(
        map((programUsers: IProgramUser[]) =>
          this.programUserService.addProgramUserToCollectionIfMissing(programUsers, ...(this.editForm.get('managers')!.value ?? []))
        )
      )
      .subscribe((programUsers: IProgramUser[]) => (this.programUsersSharedCollection = programUsers));
  }

  protected createFromForm(): IFoundationalData {
    return {
      ...new FoundationalData(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      foundationalDataType: this.editForm.get(['foundationalDataType'])!.value,
      managers: this.editForm.get(['managers'])!.value,
    };
  }
}
