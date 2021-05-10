import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkLocation } from '../work-location.model';

@Component({
  selector: 'jhi-work-location-detail',
  templateUrl: './work-location-detail.component.html',
})
export class WorkLocationDetailComponent implements OnInit {
  workLocation: IWorkLocation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workLocation }) => {
      this.workLocation = workLocation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
