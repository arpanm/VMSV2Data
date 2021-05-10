import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProgram, Program } from '../program.model';
import { ProgramService } from '../service/program.service';

@Component({
  selector: 'jhi-program-update',
  templateUrl: './program-update.component.html',
})
export class ProgramUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    phoneNumber: [],
    deploymentRegion: [],
    subdomain: [],
    implementationManagerEmail: [],
    isActive: [],
    createdBy: [],
    createdAt: [],
    updatedBy: [],
    updatedAt: [],
  });

  constructor(protected programService: ProgramService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ program }) => {
      this.updateForm(program);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const program = this.createFromForm();
    if (program.id !== undefined) {
      this.subscribeToSaveResponse(this.programService.update(program));
    } else {
      this.subscribeToSaveResponse(this.programService.create(program));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgram>>): void {
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

  protected updateForm(program: IProgram): void {
    this.editForm.patchValue({
      id: program.id,
      name: program.name,
      email: program.email,
      phoneNumber: program.phoneNumber,
      deploymentRegion: program.deploymentRegion,
      subdomain: program.subdomain,
      implementationManagerEmail: program.implementationManagerEmail,
      isActive: program.isActive,
      createdBy: program.createdBy,
      createdAt: program.createdAt,
      updatedBy: program.updatedBy,
      updatedAt: program.updatedAt,
    });
  }

  protected createFromForm(): IProgram {
    return {
      ...new Program(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      deploymentRegion: this.editForm.get(['deploymentRegion'])!.value,
      subdomain: this.editForm.get(['subdomain'])!.value,
      implementationManagerEmail: this.editForm.get(['implementationManagerEmail'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
    };
  }
}
