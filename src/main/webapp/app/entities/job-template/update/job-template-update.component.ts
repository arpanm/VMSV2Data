import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IJobTemplate, JobTemplate } from '../job-template.model';
import { JobTemplateService } from '../service/job-template.service';
import { IJobCategoryTitle } from 'app/entities/job-category-title/job-category-title.model';
import { JobCategoryTitleService } from 'app/entities/job-category-title/service/job-category-title.service';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';
import { HierarchyService } from 'app/entities/hierarchy/service/hierarchy.service';

@Component({
  selector: 'jhi-job-template-update',
  templateUrl: './job-template-update.component.html',
})
export class JobTemplateUpdateComponent implements OnInit {
  isSaving = false;

  jobCategoryTitlesSharedCollection: IJobCategoryTitle[] = [];
  hierarchiesSharedCollection: IHierarchy[] = [];

  editForm = this.fb.group({
    id: [],
    templateTitle: [],
    description: [],
    jobLevel: [],
    isDescriptionEditable: [],
    distributionType: [],
    distributionLimit: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    category: [],
    hierarchy: [],
  });

  constructor(
    protected jobTemplateService: JobTemplateService,
    protected jobCategoryTitleService: JobCategoryTitleService,
    protected hierarchyService: HierarchyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobTemplate }) => {
      this.updateForm(jobTemplate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobTemplate = this.createFromForm();
    if (jobTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.jobTemplateService.update(jobTemplate));
    } else {
      this.subscribeToSaveResponse(this.jobTemplateService.create(jobTemplate));
    }
  }

  trackJobCategoryTitleById(index: number, item: IJobCategoryTitle): number {
    return item.id!;
  }

  trackHierarchyById(index: number, item: IHierarchy): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobTemplate>>): void {
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

  protected updateForm(jobTemplate: IJobTemplate): void {
    this.editForm.patchValue({
      id: jobTemplate.id,
      templateTitle: jobTemplate.templateTitle,
      description: jobTemplate.description,
      jobLevel: jobTemplate.jobLevel,
      isDescriptionEditable: jobTemplate.isDescriptionEditable,
      distributionType: jobTemplate.distributionType,
      distributionLimit: jobTemplate.distributionLimit,
      isActive: jobTemplate.isActive,
      createdBy: jobTemplate.createdBy,
      createdAt: jobTemplate.createdAt,
      updatedBy: jobTemplate.updatedBy,
      updatedAt: jobTemplate.updatedAt,
      category: jobTemplate.category,
      hierarchy: jobTemplate.hierarchy,
    });

    this.jobCategoryTitlesSharedCollection = this.jobCategoryTitleService.addJobCategoryTitleToCollectionIfMissing(
      this.jobCategoryTitlesSharedCollection,
      jobTemplate.category
    );
    this.hierarchiesSharedCollection = this.hierarchyService.addHierarchyToCollectionIfMissing(
      this.hierarchiesSharedCollection,
      jobTemplate.hierarchy
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jobCategoryTitleService
      .query()
      .pipe(map((res: HttpResponse<IJobCategoryTitle[]>) => res.body ?? []))
      .pipe(
        map((jobCategoryTitles: IJobCategoryTitle[]) =>
          this.jobCategoryTitleService.addJobCategoryTitleToCollectionIfMissing(jobCategoryTitles, this.editForm.get('category')!.value)
        )
      )
      .subscribe((jobCategoryTitles: IJobCategoryTitle[]) => (this.jobCategoryTitlesSharedCollection = jobCategoryTitles));

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

  protected createFromForm(): IJobTemplate {
    return {
      ...new JobTemplate(),
      id: this.editForm.get(['id'])!.value,
      templateTitle: this.editForm.get(['templateTitle'])!.value,
      description: this.editForm.get(['description'])!.value,
      jobLevel: this.editForm.get(['jobLevel'])!.value,
      isDescriptionEditable: this.editForm.get(['isDescriptionEditable'])!.value,
      distributionType: this.editForm.get(['distributionType'])!.value,
      distributionLimit: this.editForm.get(['distributionLimit'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      category: this.editForm.get(['category'])!.value,
      hierarchy: this.editForm.get(['hierarchy'])!.value,
    };
  }
}
