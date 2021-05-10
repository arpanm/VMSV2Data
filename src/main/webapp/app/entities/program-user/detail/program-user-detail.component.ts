import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramUser } from '../program-user.model';

@Component({
  selector: 'jhi-program-user-detail',
  templateUrl: './program-user-detail.component.html',
})
export class ProgramUserDetailComponent implements OnInit {
  programUser: IProgramUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programUser }) => {
      this.programUser = programUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
