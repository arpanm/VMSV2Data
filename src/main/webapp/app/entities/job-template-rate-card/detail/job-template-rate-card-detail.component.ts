import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobTemplateRateCard } from '../job-template-rate-card.model';

@Component({
  selector: 'jhi-job-template-rate-card-detail',
  templateUrl: './job-template-rate-card-detail.component.html',
})
export class JobTemplateRateCardDetailComponent implements OnInit {
  jobTemplateRateCard: IJobTemplateRateCard | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobTemplateRateCard }) => {
      this.jobTemplateRateCard = jobTemplateRateCard;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
