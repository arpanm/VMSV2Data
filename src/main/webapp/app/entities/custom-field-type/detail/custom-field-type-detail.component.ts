import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomFieldType } from '../custom-field-type.model';

@Component({
  selector: 'jhi-custom-field-type-detail',
  templateUrl: './custom-field-type-detail.component.html',
})
export class CustomFieldTypeDetailComponent implements OnInit {
  customFieldType: ICustomFieldType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customFieldType }) => {
      this.customFieldType = customFieldType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
