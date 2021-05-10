import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoundationalData } from '../foundational-data.model';

@Component({
  selector: 'jhi-foundational-data-detail',
  templateUrl: './foundational-data-detail.component.html',
})
export class FoundationalDataDetailComponent implements OnInit {
  foundationalData: IFoundationalData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foundationalData }) => {
      this.foundationalData = foundationalData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
