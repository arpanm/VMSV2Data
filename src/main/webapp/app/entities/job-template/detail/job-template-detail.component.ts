import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobTemplate } from '../job-template.model';

@Component({
  selector: 'jhi-job-template-detail',
  templateUrl: './job-template-detail.component.html',
})
export class JobTemplateDetailComponent implements OnInit {
  jobTemplate: IJobTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobTemplate }) => {
      this.jobTemplate = jobTemplate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
