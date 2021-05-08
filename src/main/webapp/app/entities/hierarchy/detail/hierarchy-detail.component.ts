import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHierarchy } from '../hierarchy.model';

@Component({
  selector: 'jhi-hierarchy-detail',
  templateUrl: './hierarchy-detail.component.html',
})
export class HierarchyDetailComponent implements OnInit {
  hierarchy: IHierarchy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hierarchy }) => {
      this.hierarchy = hierarchy;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
