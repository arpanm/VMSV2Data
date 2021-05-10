import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IJobTemplateRateCard, JobTemplateRateCard } from '../job-template-rate-card.model';
import { JobTemplateRateCardService } from '../service/job-template-rate-card.service';
import { IJobTemplate } from 'app/entities/job-template/job-template.model';
import { JobTemplateService } from 'app/entities/job-template/service/job-template.service';
import { IJobCategoryTitle } from 'app/entities/job-category-title/job-category-title.model';
import { JobCategoryTitleService } from 'app/entities/job-category-title/service/job-category-title.service';

@Component({
  selector: 'jhi-job-template-rate-card-update',
  templateUrl: './job-template-rate-card-update.component.html',
})
export class JobTemplateRateCardUpdateComponent implements OnInit {
  isSaving = false;

  jobTemplatesSharedCollection: IJobTemplate[] = [];
  jobCategoryTitlesSharedCollection: IJobCategoryTitle[] = [];

  editForm = this.fb.group({
    id: [],
    currency: [],
    hourlyMin: [],
    hourlyMax: [],
    dailyMin: [],
    dailyMax: [],
    weeklyMin: [],
    weeklyMax: [],
    monthlyMin: [],
    monthlyMax: [],
    yearlyMin: [],
    yearlyMax: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    template: [],
    category: [],
  });

  constructor(
    protected jobTemplateRateCardService: JobTemplateRateCardService,
    protected jobTemplateService: JobTemplateService,
    protected jobCategoryTitleService: JobCategoryTitleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobTemplateRateCard }) => {
      this.updateForm(jobTemplateRateCard);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobTemplateRateCard = this.createFromForm();
    if (jobTemplateRateCard.id !== undefined) {
      this.subscribeToSaveResponse(this.jobTemplateRateCardService.update(jobTemplateRateCard));
    } else {
      this.subscribeToSaveResponse(this.jobTemplateRateCardService.create(jobTemplateRateCard));
    }
  }

  trackJobTemplateById(index: number, item: IJobTemplate): number {
    return item.id!;
  }

  trackJobCategoryTitleById(index: number, item: IJobCategoryTitle): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobTemplateRateCard>>): void {
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

  protected updateForm(jobTemplateRateCard: IJobTemplateRateCard): void {
    this.editForm.patchValue({
      id: jobTemplateRateCard.id,
      currency: jobTemplateRateCard.currency,
      hourlyMin: jobTemplateRateCard.hourlyMin,
      hourlyMax: jobTemplateRateCard.hourlyMax,
      dailyMin: jobTemplateRateCard.dailyMin,
      dailyMax: jobTemplateRateCard.dailyMax,
      weeklyMin: jobTemplateRateCard.weeklyMin,
      weeklyMax: jobTemplateRateCard.weeklyMax,
      monthlyMin: jobTemplateRateCard.monthlyMin,
      monthlyMax: jobTemplateRateCard.monthlyMax,
      yearlyMin: jobTemplateRateCard.yearlyMin,
      yearlyMax: jobTemplateRateCard.yearlyMax,
      isActive: jobTemplateRateCard.isActive,
      createdBy: jobTemplateRateCard.createdBy,
      createdAt: jobTemplateRateCard.createdAt,
      updatedBy: jobTemplateRateCard.updatedBy,
      updatedAt: jobTemplateRateCard.updatedAt,
      template: jobTemplateRateCard.template,
      category: jobTemplateRateCard.category,
    });

    this.jobTemplatesSharedCollection = this.jobTemplateService.addJobTemplateToCollectionIfMissing(
      this.jobTemplatesSharedCollection,
      jobTemplateRateCard.template
    );
    this.jobCategoryTitlesSharedCollection = this.jobCategoryTitleService.addJobCategoryTitleToCollectionIfMissing(
      this.jobCategoryTitlesSharedCollection,
      jobTemplateRateCard.category
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jobTemplateService
      .query()
      .pipe(map((res: HttpResponse<IJobTemplate[]>) => res.body ?? []))
      .pipe(
        map((jobTemplates: IJobTemplate[]) =>
          this.jobTemplateService.addJobTemplateToCollectionIfMissing(jobTemplates, this.editForm.get('template')!.value)
        )
      )
      .subscribe((jobTemplates: IJobTemplate[]) => (this.jobTemplatesSharedCollection = jobTemplates));

    this.jobCategoryTitleService
      .query()
      .pipe(map((res: HttpResponse<IJobCategoryTitle[]>) => res.body ?? []))
      .pipe(
        map((jobCategoryTitles: IJobCategoryTitle[]) =>
          this.jobCategoryTitleService.addJobCategoryTitleToCollectionIfMissing(jobCategoryTitles, this.editForm.get('category')!.value)
        )
      )
      .subscribe((jobCategoryTitles: IJobCategoryTitle[]) => (this.jobCategoryTitlesSharedCollection = jobCategoryTitles));
  }

  protected createFromForm(): IJobTemplateRateCard {
    return {
      ...new JobTemplateRateCard(),
      id: this.editForm.get(['id'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      hourlyMin: this.editForm.get(['hourlyMin'])!.value,
      hourlyMax: this.editForm.get(['hourlyMax'])!.value,
      dailyMin: this.editForm.get(['dailyMin'])!.value,
      dailyMax: this.editForm.get(['dailyMax'])!.value,
      weeklyMin: this.editForm.get(['weeklyMin'])!.value,
      weeklyMax: this.editForm.get(['weeklyMax'])!.value,
      monthlyMin: this.editForm.get(['monthlyMin'])!.value,
      monthlyMax: this.editForm.get(['monthlyMax'])!.value,
      yearlyMin: this.editForm.get(['yearlyMin'])!.value,
      yearlyMax: this.editForm.get(['yearlyMax'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      template: this.editForm.get(['template'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }
}
