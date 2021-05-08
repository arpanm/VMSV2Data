jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HierarchyService } from '../service/hierarchy.service';
import { IHierarchy, Hierarchy } from '../hierarchy.model';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IProgramUser } from 'app/entities/program-user/program-user.model';
import { ProgramUserService } from 'app/entities/program-user/service/program-user.service';

import { HierarchyUpdateComponent } from './hierarchy-update.component';

describe('Component Tests', () => {
  describe('Hierarchy Management Update Component', () => {
    let comp: HierarchyUpdateComponent;
    let fixture: ComponentFixture<HierarchyUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hierarchyService: HierarchyService;
    let programService: ProgramService;
    let programUserService: ProgramUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HierarchyUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HierarchyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HierarchyUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hierarchyService = TestBed.inject(HierarchyService);
      programService = TestBed.inject(ProgramService);
      programUserService = TestBed.inject(ProgramUserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Program query and add missing value', () => {
        const hierarchy: IHierarchy = { id: 456 };
        const client: IProgram = { id: 78868 };
        hierarchy.client = client;

        const programCollection: IProgram[] = [{ id: 55445 }];
        spyOn(programService, 'query').and.returnValue(of(new HttpResponse({ body: programCollection })));
        const additionalPrograms = [client];
        const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
        spyOn(programService, 'addProgramToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        expect(programService.query).toHaveBeenCalled();
        expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(programCollection, ...additionalPrograms);
        expect(comp.programsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Hierarchy query and add missing value', () => {
        const hierarchy: IHierarchy = { id: 456 };
        const parent: IHierarchy = { id: 3548 };
        hierarchy.parent = parent;

        const hierarchyCollection: IHierarchy[] = [{ id: 83122 }];
        spyOn(hierarchyService, 'query').and.returnValue(of(new HttpResponse({ body: hierarchyCollection })));
        const additionalHierarchies = [parent];
        const expectedCollection: IHierarchy[] = [...additionalHierarchies, ...hierarchyCollection];
        spyOn(hierarchyService, 'addHierarchyToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        expect(hierarchyService.query).toHaveBeenCalled();
        expect(hierarchyService.addHierarchyToCollectionIfMissing).toHaveBeenCalledWith(hierarchyCollection, ...additionalHierarchies);
        expect(comp.hierarchiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ProgramUser query and add missing value', () => {
        const hierarchy: IHierarchy = { id: 456 };
        const managers: IProgramUser[] = [{ id: 55091 }];
        hierarchy.managers = managers;

        const programUserCollection: IProgramUser[] = [{ id: 5552 }];
        spyOn(programUserService, 'query').and.returnValue(of(new HttpResponse({ body: programUserCollection })));
        const additionalProgramUsers = [...managers];
        const expectedCollection: IProgramUser[] = [...additionalProgramUsers, ...programUserCollection];
        spyOn(programUserService, 'addProgramUserToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        expect(programUserService.query).toHaveBeenCalled();
        expect(programUserService.addProgramUserToCollectionIfMissing).toHaveBeenCalledWith(
          programUserCollection,
          ...additionalProgramUsers
        );
        expect(comp.programUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const hierarchy: IHierarchy = { id: 456 };
        const client: IProgram = { id: 32281 };
        hierarchy.client = client;
        const parent: IHierarchy = { id: 57766 };
        hierarchy.parent = parent;
        const managers: IProgramUser = { id: 69266 };
        hierarchy.managers = [managers];

        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hierarchy));
        expect(comp.programsSharedCollection).toContain(client);
        expect(comp.hierarchiesSharedCollection).toContain(parent);
        expect(comp.programUsersSharedCollection).toContain(managers);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hierarchy = { id: 123 };
        spyOn(hierarchyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hierarchy }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hierarchyService.update).toHaveBeenCalledWith(hierarchy);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hierarchy = new Hierarchy();
        spyOn(hierarchyService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hierarchy }));
        saveSubject.complete();

        // THEN
        expect(hierarchyService.create).toHaveBeenCalledWith(hierarchy);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hierarchy = { id: 123 };
        spyOn(hierarchyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hierarchy });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hierarchyService.update).toHaveBeenCalledWith(hierarchy);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackProgramById', () => {
        it('Should return tracked Program primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProgramById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackHierarchyById', () => {
        it('Should return tracked Hierarchy primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHierarchyById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProgramUserById', () => {
        it('Should return tracked ProgramUser primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProgramUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedProgramUser', () => {
        it('Should return option if no ProgramUser is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedProgramUser(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected ProgramUser for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedProgramUser(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this ProgramUser is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedProgramUser(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
