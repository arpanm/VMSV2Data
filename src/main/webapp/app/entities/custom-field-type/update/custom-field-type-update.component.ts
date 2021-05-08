import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICustomFieldType, CustomFieldType } from '../custom-field-type.model';
import { CustomFieldTypeService } from '../service/custom-field-type.service';
import { IFoundationalDataType } from 'app/entities/foundational-data-type/foundational-data-type.model';
import { FoundationalDataTypeService } from 'app/entities/foundational-data-type/service/foundational-data-type.service';

@Component({
  selector: 'jhi-custom-field-type-update',
  templateUrl: './custom-field-type-update.component.html',
})
export class CustomFieldTypeUpdateComponent implements OnInit {
  isSaving = false;

  foundationalDataTypesSharedCollection: IFoundationalDataType[] = [];

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
  });

  constructor(
    protected customFieldTypeService: CustomFieldTypeService,
    protected foundationalDataTypeService: FoundationalDataTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customFieldType }) => {
      this.updateForm(customFieldType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customFieldType = this.createFromForm();
    if (customFieldType.id !== undefined) {
      this.subscribeToSaveResponse(this.customFieldTypeService.update(customFieldType));
    } else {
      this.subscribeToSaveResponse(this.customFieldTypeService.create(customFieldType));
    }
  }

  trackFoundationalDataTypeById(index: number, item: IFoundationalDataType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomFieldType>>): void {
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

  protected updateForm(customFieldType: ICustomFieldType): void {
    this.editForm.patchValue({
      id: customFieldType.id,
      name: customFieldType.name,
      code: customFieldType.code,
      description: customFieldType.description,
      isActive: customFieldType.isActive,
      createdBy: customFieldType.createdBy,
      createdAt: customFieldType.createdAt,
      updatedBy: customFieldType.updatedBy,
      updatedAt: customFieldType.updatedAt,
      foundationalDataType: customFieldType.foundationalDataType,
    });

    this.foundationalDataTypesSharedCollection = this.foundationalDataTypeService.addFoundationalDataTypeToCollectionIfMissing(
      this.foundationalDataTypesSharedCollection,
      customFieldType.foundationalDataType
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
  }

  protected createFromForm(): ICustomFieldType {
    return {
      ...new CustomFieldType(),
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
    };
  }
}
