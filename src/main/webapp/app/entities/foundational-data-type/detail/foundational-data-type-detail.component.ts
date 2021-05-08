import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoundationalDataType } from '../foundational-data-type.model';

@Component({
  selector: 'jhi-foundational-data-type-detail',
  templateUrl: './foundational-data-type-detail.component.html',
})
export class FoundationalDataTypeDetailComponent implements OnInit {
  foundationalDataType: IFoundationalDataType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foundationalDataType }) => {
      this.foundationalDataType = foundationalDataType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
