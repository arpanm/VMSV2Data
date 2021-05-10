import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomFieldData } from '../custom-field-data.model';

@Component({
  selector: 'jhi-custom-field-data-detail',
  templateUrl: './custom-field-data-detail.component.html',
})
export class CustomFieldDataDetailComponent implements OnInit {
  customFieldData: ICustomFieldData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customFieldData }) => {
      this.customFieldData = customFieldData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
