import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHierarchy, Hierarchy } from '../hierarchy.model';
import { HierarchyService } from '../service/hierarchy.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IProgramUser } from 'app/entities/program-user/program-user.model';
import { ProgramUserService } from 'app/entities/program-user/service/program-user.service';

@Component({
  selector: 'jhi-hierarchy-update',
  templateUrl: './hierarchy-update.component.html',
})
export class HierarchyUpdateComponent implements OnInit {
  isSaving = false;

  programsSharedCollection: IProgram[] = [];
  hierarchiesSharedCollection: IHierarchy[] = [];
  programUsersSharedCollection: IProgramUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    workFlow: [],
    preferredLanguage: [],
    preferredCurrency: [],
    primaryCountry: [],
    primaryAddress: [],
    secondaryCountry: [],
    secondaryAddress: [],
    primaryContactName: [],
    primaryContactDesignation: [],
    primaryContactEmail: [],
    primaryContactPhone: [],
    secondaryContactName: [],
    secondaryContactDesignation: [],
    secondaryContactEmail: [],
    secondaryContactPhone: [],
    fileUploadPath: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
    client: [],
    parent: [],
    managers: [],
  });

  constructor(
    protected hierarchyService: HierarchyService,
    protected programService: ProgramService,
    protected programUserService: ProgramUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hierarchy }) => {
      this.updateForm(hierarchy);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hierarchy = this.createFromForm();
    if (hierarchy.id !== undefined) {
      this.subscribeToSaveResponse(this.hierarchyService.update(hierarchy));
    } else {
      this.subscribeToSaveResponse(this.hierarchyService.create(hierarchy));
    }
  }

  trackProgramById(index: number, item: IProgram): number {
    return item.id!;
  }

  trackHierarchyById(index: number, item: IHierarchy): number {
    return item.id!;
  }

  trackProgramUserById(index: number, item: IProgramUser): number {
    return item.id!;
  }

  getSelectedProgramUser(option: IProgramUser, selectedVals?: IProgramUser[]): IProgramUser {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHierarchy>>): void {
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

  protected updateForm(hierarchy: IHierarchy): void {
    this.editForm.patchValue({
      id: hierarchy.id,
      name: hierarchy.name,
      workFlow: hierarchy.workFlow,
      preferredLanguage: hierarchy.preferredLanguage,
      preferredCurrency: hierarchy.preferredCurrency,
      primaryCountry: hierarchy.primaryCountry,
      primaryAddress: hierarchy.primaryAddress,
      secondaryCountry: hierarchy.secondaryCountry,
      secondaryAddress: hierarchy.secondaryAddress,
      primaryContactName: hierarchy.primaryContactName,
      primaryContactDesignation: hierarchy.primaryContactDesignation,
      primaryContactEmail: hierarchy.primaryContactEmail,
      primaryContactPhone: hierarchy.primaryContactPhone,
      secondaryContactName: hierarchy.secondaryContactName,
      secondaryContactDesignation: hierarchy.secondaryContactDesignation,
      secondaryContactEmail: hierarchy.secondaryContactEmail,
      secondaryContactPhone: hierarchy.secondaryContactPhone,
      fileUploadPath: hierarchy.fileUploadPath,
      isActive: hierarchy.isActive,
      createdBy: hierarchy.createdBy,
      createdAt: hierarchy.createdAt,
      updatedBy: hierarchy.updatedBy,
      updatedAt: hierarchy.updatedAt,
      client: hierarchy.client,
      parent: hierarchy.parent,
      managers: hierarchy.managers,
    });

    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing(this.programsSharedCollection, hierarchy.client);
    this.hierarchiesSharedCollection = this.hierarchyService.addHierarchyToCollectionIfMissing(
      this.hierarchiesSharedCollection,
      hierarchy.parent
    );
    this.programUsersSharedCollection = this.programUserService.addProgramUserToCollectionIfMissing(
      this.programUsersSharedCollection,
      ...(hierarchy.managers ?? [])
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

    this.hierarchyService
      .query()
      .pipe(map((res: HttpResponse<IHierarchy[]>) => res.body ?? []))
      .pipe(
        map((hierarchies: IHierarchy[]) =>
          this.hierarchyService.addHierarchyToCollectionIfMissing(hierarchies, this.editForm.get('parent')!.value)
        )
      )
      .subscribe((hierarchies: IHierarchy[]) => (this.hierarchiesSharedCollection = hierarchies));

    this.programUserService
      .query()
      .pipe(map((res: HttpResponse<IProgramUser[]>) => res.body ?? []))
      .pipe(
        map((programUsers: IProgramUser[]) =>
          this.programUserService.addProgramUserToCollectionIfMissing(programUsers, ...(this.editForm.get('managers')!.value ?? []))
        )
      )
      .subscribe((programUsers: IProgramUser[]) => (this.programUsersSharedCollection = programUsers));
  }

  protected createFromForm(): IHierarchy {
    return {
      ...new Hierarchy(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      workFlow: this.editForm.get(['workFlow'])!.value,
      preferredLanguage: this.editForm.get(['preferredLanguage'])!.value,
      preferredCurrency: this.editForm.get(['preferredCurrency'])!.value,
      primaryCountry: this.editForm.get(['primaryCountry'])!.value,
      primaryAddress: this.editForm.get(['primaryAddress'])!.value,
      secondaryCountry: this.editForm.get(['secondaryCountry'])!.value,
      secondaryAddress: this.editForm.get(['secondaryAddress'])!.value,
      primaryContactName: this.editForm.get(['primaryContactName'])!.value,
      primaryContactDesignation: this.editForm.get(['primaryContactDesignation'])!.value,
      primaryContactEmail: this.editForm.get(['primaryContactEmail'])!.value,
      primaryContactPhone: this.editForm.get(['primaryContactPhone'])!.value,
      secondaryContactName: this.editForm.get(['secondaryContactName'])!.value,
      secondaryContactDesignation: this.editForm.get(['secondaryContactDesignation'])!.value,
      secondaryContactEmail: this.editForm.get(['secondaryContactEmail'])!.value,
      secondaryContactPhone: this.editForm.get(['secondaryContactPhone'])!.value,
      fileUploadPath: this.editForm.get(['fileUploadPath'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      client: this.editForm.get(['client'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      managers: this.editForm.get(['managers'])!.value,
    };
  }
}
