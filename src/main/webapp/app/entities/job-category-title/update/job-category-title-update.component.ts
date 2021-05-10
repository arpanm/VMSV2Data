import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IJobCategoryTitle, JobCategoryTitle } from '../job-category-title.model';
import { JobCategoryTitleService } from '../service/job-category-title.service';

@Component({
  selector: 'jhi-job-category-title-update',
  templateUrl: './job-category-title-update.component.html',
})
export class JobCategoryTitleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    category: [],
    title: [],
    code: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
  });

  constructor(
    protected jobCategoryTitleService: JobCategoryTitleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobCategoryTitle }) => {
      this.updateForm(jobCategoryTitle);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobCategoryTitle = this.createFromForm();
    if (jobCategoryTitle.id !== undefined) {
      this.subscribeToSaveResponse(this.jobCategoryTitleService.update(jobCategoryTitle));
    } else {
      this.subscribeToSaveResponse(this.jobCategoryTitleService.create(jobCategoryTitle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobCategoryTitle>>): void {
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

  protected updateForm(jobCategoryTitle: IJobCategoryTitle): void {
    this.editForm.patchValue({
      id: jobCategoryTitle.id,
      category: jobCategoryTitle.category,
      title: jobCategoryTitle.title,
      code: jobCategoryTitle.code,
      isActive: jobCategoryTitle.isActive,
      createdBy: jobCategoryTitle.createdBy,
      createdAt: jobCategoryTitle.createdAt,
      updatedBy: jobCategoryTitle.updatedBy,
      updatedAt: jobCategoryTitle.updatedAt,
    });
  }

  protected createFromForm(): IJobCategoryTitle {
    return {
      ...new JobCategoryTitle(),
      id: this.editForm.get(['id'])!.value,
      category: this.editForm.get(['category'])!.value,
      title: this.editForm.get(['title'])!.value,
      code: this.editForm.get(['code'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
    };
  }
}
