import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICustomFieldData, CustomFieldData } from '../custom-field-data.model';
import { CustomFieldDataService } from '../service/custom-field-data.service';
import { IFoundationalData } from 'app/entities/foundational-data/foundational-data.model';
import { FoundationalDataService } from 'app/entities/foundational-data/service/foundational-data.service';
import { ICustomFieldType } from 'app/entities/custom-field-type/custom-field-type.model';
import { CustomFieldTypeService } from 'app/entities/custom-field-type/service/custom-field-type.service';

@Component({
  selector: 'jhi-custom-field-data-update',
  templateUrl: './custom-field-data-update.component.html',
})
export class CustomFieldDataUpdateComponent implements OnInit {
  isSaving = false;

  foundationalDataSharedCollection: IFoundationalData[] = [];
  customFieldTypesSharedCollection: ICustomFieldType[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    foundationalData: [],
    customFieldType: [],
  });

  constructor(
    protected customFieldDataService: CustomFieldDataService,
    protected foundationalDataService: FoundationalDataService,
    protected customFieldTypeService: CustomFieldTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customFieldData }) => {
      this.updateForm(customFieldData);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customFieldData = this.createFromForm();
    if (customFieldData.id !== undefined) {
      this.subscribeToSaveResponse(this.customFieldDataService.update(customFieldData));
    } else {
      this.subscribeToSaveResponse(this.customFieldDataService.create(customFieldData));
    }
  }

  trackFoundationalDataById(index: number, item: IFoundationalData): number {
    return item.id!;
  }

  trackCustomFieldTypeById(index: number, item: ICustomFieldType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomFieldData>>): void {
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

  protected updateForm(customFieldData: ICustomFieldData): void {
    this.editForm.patchValue({
      id: customFieldData.id,
      value: customFieldData.value,
      isActive: customFieldData.isActive,
      createdBy: customFieldData.createdBy,
      createdAt: customFieldData.createdAt,
      updatedBy: customFieldData.updatedBy,
      updatedAt: customFieldData.updatedAt,
      foundationalData: customFieldData.foundationalData,
      customFieldType: customFieldData.customFieldType,
    });

    this.foundationalDataSharedCollection = this.foundationalDataService.addFoundationalDataToCollectionIfMissing(
      this.foundationalDataSharedCollection,
      customFieldData.foundationalData
    );
    this.customFieldTypesSharedCollection = this.customFieldTypeService.addCustomFieldTypeToCollectionIfMissing(
      this.customFieldTypesSharedCollection,
      customFieldData.customFieldType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.foundationalDataService
      .query()
      .pipe(map((res: HttpResponse<IFoundationalData[]>) => res.body ?? []))
      .pipe(
        map((foundationalData: IFoundationalData[]) =>
          this.foundationalDataService.addFoundationalDataToCollectionIfMissing(
            foundationalData,
            this.editForm.get('foundationalData')!.value
          )
        )
      )
      .subscribe((foundationalData: IFoundationalData[]) => (this.foundationalDataSharedCollection = foundationalData));

    this.customFieldTypeService
      .query()
      .pipe(map((res: HttpResponse<ICustomFieldType[]>) => res.body ?? []))
      .pipe(
        map((customFieldTypes: ICustomFieldType[]) =>
          this.customFieldTypeService.addCustomFieldTypeToCollectionIfMissing(customFieldTypes, this.editForm.get('customFieldType')!.value)
        )
      )
      .subscribe((customFieldTypes: ICustomFieldType[]) => (this.customFieldTypesSharedCollection = customFieldTypes));
  }

  protected createFromForm(): ICustomFieldData {
    return {
      ...new CustomFieldData(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      foundationalData: this.editForm.get(['foundationalData'])!.value,
      customFieldType: this.editForm.get(['customFieldType'])!.value,
    };
  }
}
