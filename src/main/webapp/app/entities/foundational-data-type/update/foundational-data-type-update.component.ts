import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFoundationalDataType, FoundationalDataType } from '../foundational-data-type.model';
import { FoundationalDataTypeService } from '../service/foundational-data-type.service';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';
import { HierarchyService } from 'app/entities/hierarchy/service/hierarchy.service';

@Component({
  selector: 'jhi-foundational-data-type-update',
  templateUrl: './foundational-data-type-update.component.html',
})
export class FoundationalDataTypeUpdateComponent implements OnInit {
  isSaving = false;

  hierarchiesSharedCollection: IHierarchy[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    requiredInHierarchy: [],
    requiredInJobs: [],
    requiredInSow: [],
    fileUploadPath: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    hierarchy: [],
  });

  constructor(
    protected foundationalDataTypeService: FoundationalDataTypeService,
    protected hierarchyService: HierarchyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foundationalDataType }) => {
      this.updateForm(foundationalDataType);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const foundationalDataType = this.createFromForm();
    if (foundationalDataType.id !== undefined) {
      this.subscribeToSaveResponse(this.foundationalDataTypeService.update(foundationalDataType));
    } else {
      this.subscribeToSaveResponse(this.foundationalDataTypeService.create(foundationalDataType));
    }
  }

  trackHierarchyById(index: number, item: IHierarchy): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoundationalDataType>>): void {
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

  protected updateForm(foundationalDataType: IFoundationalDataType): void {
    this.editForm.patchValue({
      id: foundationalDataType.id,
      name: foundationalDataType.name,
      description: foundationalDataType.description,
      requiredInHierarchy: foundationalDataType.requiredInHierarchy,
      requiredInJobs: foundationalDataType.requiredInJobs,
      requiredInSow: foundationalDataType.requiredInSow,
      fileUploadPath: foundationalDataType.fileUploadPath,
      isActive: foundationalDataType.isActive,
      createdBy: foundationalDataType.createdBy,
      createdAt: foundationalDataType.createdAt,
      updatedBy: foundationalDataType.updatedBy,
      updatedAt: foundationalDataType.updatedAt,
      hierarchy: foundationalDataType.hierarchy,
    });

    this.hierarchiesSharedCollection = this.hierarchyService.addHierarchyToCollectionIfMissing(
      this.hierarchiesSharedCollection,
      foundationalDataType.hierarchy
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hierarchyService
      .query()
      .pipe(map((res: HttpResponse<IHierarchy[]>) => res.body ?? []))
      .pipe(
        map((hierarchies: IHierarchy[]) =>
          this.hierarchyService.addHierarchyToCollectionIfMissing(hierarchies, this.editForm.get('hierarchy')!.value)
        )
      )
      .subscribe((hierarchies: IHierarchy[]) => (this.hierarchiesSharedCollection = hierarchies));
  }

  protected createFromForm(): IFoundationalDataType {
    return {
      ...new FoundationalDataType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      requiredInHierarchy: this.editForm.get(['requiredInHierarchy'])!.value,
      requiredInJobs: this.editForm.get(['requiredInJobs'])!.value,
      requiredInSow: this.editForm.get(['requiredInSow'])!.value,
      fileUploadPath: this.editForm.get(['fileUploadPath'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      hierarchy: this.editForm.get(['hierarchy'])!.value,
    };
  }
}
