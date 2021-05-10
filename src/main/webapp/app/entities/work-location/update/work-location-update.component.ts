import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IWorkLocation, WorkLocation } from '../work-location.model';
import { WorkLocationService } from '../service/work-location.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';

@Component({
  selector: 'jhi-work-location-update',
  templateUrl: './work-location-update.component.html',
})
export class WorkLocationUpdateComponent implements OnInit {
  isSaving = false;

  programsSharedCollection: IProgram[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: [],
    country: [],
    state: [],
    address: [],
    fileUploadPath: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    client: [],
  });

  constructor(
    protected workLocationService: WorkLocationService,
    protected programService: ProgramService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workLocation }) => {
      this.updateForm(workLocation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workLocation = this.createFromForm();
    if (workLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.workLocationService.update(workLocation));
    } else {
      this.subscribeToSaveResponse(this.workLocationService.create(workLocation));
    }
  }

  trackProgramById(index: number, item: IProgram): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkLocation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(workLocation: IWorkLocation): void {
    this.editForm.patchValue({
      id: workLocation.id,
      code: workLocation.code,
      name: workLocation.name,
      description: workLocation.description,
      country: workLocation.country,
      state: workLocation.state,
      address: workLocation.address,
      fileUploadPath: workLocation.fileUploadPath,
      isActive: workLocation.isActive,
      createdBy: workLocation.createdBy,
      createdAt: workLocation.createdAt,
      updatedBy: workLocation.updatedBy,
      updatedAt: workLocation.updatedAt,
      client: workLocation.client,
    });

    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing(this.programsSharedCollection, workLocation.client);
  }

  protected loadRelationshipsOptions(): void {
    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) => this.programService.addProgramToCollectionIfMissing(programs, this.editForm.get('client')!.value))
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));
  }

  protected createFromForm(): IWorkLocation {
    return {
      ...new WorkLocation(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      country: this.editForm.get(['country'])!.value,
      state: this.editForm.get(['state'])!.value,
      address: this.editForm.get(['address'])!.value,
      fileUploadPath: this.editForm.get(['fileUploadPath'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      client: this.editForm.get(['client'])!.value,
    };
  }
}
