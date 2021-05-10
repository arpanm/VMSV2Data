import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobCategoryTitle } from '../job-category-title.model';

@Component({
  selector: 'jhi-job-category-title-detail',
  templateUrl: './job-category-title-detail.component.html',
})
export class JobCategoryTitleDetailComponent implements OnInit {
  jobCategoryTitle: IJobCategoryTitle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobCategoryTitle }) => {
      this.jobCategoryTitle = jobCategoryTitle;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
