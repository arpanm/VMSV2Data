import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProgramUser, ProgramUser } from '../program-user.model';
import { ProgramUserService } from '../service/program-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IWorkLocation } from 'app/entities/work-location/work-location.model';
import { WorkLocationService } from 'app/entities/work-location/service/work-location.service';

@Component({
  selector: 'jhi-program-user-update',
  templateUrl: './program-user-update.component.html',
})
export class ProgramUserUpdateComponent implements OnInit {
  isSaving = false;

  programsSharedCollection: IProgram[] = [];
  programUsersSharedCollection: IProgramUser[] = [];
  workLocationsSharedCollection: IWorkLocation[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    sourceId: [],
    phoneNumber: [],
    clientDesignation: [],
    simplifyRole: [],
    fileUploadPath: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    client: [],
    manager: [],
    location: [],
  });

  constructor(
    protected programUserService: ProgramUserService,
    protected programService: ProgramService,
    protected workLocationService: WorkLocationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programUser }) => {
      this.updateForm(programUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programUser = this.createFromForm();
    if (programUser.id !== undefined) {
      this.subscribeToSaveResponse(this.programUserService.update(programUser));
    } else {
      this.subscribeToSaveResponse(this.programUserService.create(programUser));
    }
  }

  trackProgramById(index: number, item: IProgram): number {
    return item.id!;
  }

  trackProgramUserById(index: number, item: IProgramUser): number {
    return item.id!;
  }

  trackWorkLocationById(index: number, item: IWorkLocation): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramUser>>): void {
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

  protected updateForm(programUser: IProgramUser): void {
    this.editForm.patchValue({
      id: programUser.id,
      firstName: programUser.firstName,
      lastName: programUser.lastName,
      email: programUser.email,
      sourceId: programUser.sourceId,
      phoneNumber: programUser.phoneNumber,
      clientDesignation: programUser.clientDesignation,
      simplifyRole: programUser.simplifyRole,
      fileUploadPath: programUser.fileUploadPath,
      isActive: programUser.isActive,
      createdBy: programUser.createdBy,
      createdAt: programUser.createdAt,
      updatedBy: programUser.updatedBy,
      updatedAt: programUser.updatedAt,
      client: programUser.client,
      manager: programUser.manager,
      location: programUser.location,
    });

    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing(this.programsSharedCollection, programUser.client);
    this.programUsersSharedCollection = this.programUserService.addProgramUserToCollectionIfMissing(
      this.programUsersSharedCollection,
      programUser.manager
    );
    this.workLocationsSharedCollection = this.workLocationService.addWorkLocationToCollectionIfMissing(
      this.workLocationsSharedCollection,
      programUser.location
    );
  }

  protected loadRelationshipsOptions(): void {
    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) => this.programService.addProgramToCollectionIfMissing(programs, this.editForm.get('client')!.value))
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));

    this.programUserService
      .query()
      .pipe(map((res: HttpResponse<IProgramUser[]>) => res.body ?? []))
      .pipe(
        map((programUsers: IProgramUser[]) =>
          this.programUserService.addProgramUserToCollectionIfMissing(programUsers, this.editForm.get('manager')!.value)
        )
      )
      .subscribe((programUsers: IProgramUser[]) => (this.programUsersSharedCollection = programUsers));

    this.workLocationService
      .query()
      .pipe(map((res: HttpResponse<IWorkLocation[]>) => res.body ?? []))
      .pipe(
        map((workLocations: IWorkLocation[]) =>
          this.workLocationService.addWorkLocationToCollectionIfMissing(workLocations, this.editForm.get('location')!.value)
        )
      )
      .subscribe((workLocations: IWorkLocation[]) => (this.workLocationsSharedCollection = workLocations));
  }

  protected createFromForm(): IProgramUser {
    return {
      ...new ProgramUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      sourceId: this.editForm.get(['sourceId'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      clientDesignation: this.editForm.get(['clientDesignation'])!.value,
      simplifyRole: this.editForm.get(['simplifyRole'])!.value,
      fileUploadPath: this.editForm.get(['fileUploadPath'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      client: this.editForm.get(['client'])!.value,
      manager: this.editForm.get(['manager'])!.value,
      location: this.editForm.get(['location'])!.value,
    };
  }
}
