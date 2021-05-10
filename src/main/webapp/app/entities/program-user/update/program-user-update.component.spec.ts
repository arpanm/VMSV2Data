jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProgramUserService } from '../service/program-user.service';
import { IProgramUser, ProgramUser } from '../program-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IWorkLocation } from 'app/entities/work-location/work-location.model';
import { WorkLocationService } from 'app/entities/work-location/service/work-location.service';

import { ProgramUserUpdateComponent } from './program-user-update.component';

describe('Component Tests', () => {
  describe('ProgramUser Management Update Component', () => {
    let comp: ProgramUserUpdateComponent;
    let fixture: ComponentFixture<ProgramUserUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let programUserService: ProgramUserService;
    let programService: ProgramService;
    let workLocationService: WorkLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProgramUserUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProgramUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramUserUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      programUserService = TestBed.inject(ProgramUserService);
      programService = TestBed.inject(ProgramService);
      workLocationService = TestBed.inject(WorkLocationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Program query and add missing value', () => {
        const programUser: IProgramUser = { id: 456 };
        const client: IProgram = { id: 98881 };
        programUser.client = client;

        const programCollection: IProgram[] = [{ id: 87739 }];
        spyOn(programService, 'query').and.returnValue(of(new HttpResponse({ body: programCollection })));
        const additionalPrograms = [client];
        const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
        spyOn(programService, 'addProgramToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        expect(programService.query).toHaveBeenCalled();
        expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(programCollection, ...additionalPrograms);
        expect(comp.programsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ProgramUser query and add missing value', () => {
        const programUser: IProgramUser = { id: 456 };
        const manager: IProgramUser = { id: 93396 };
        programUser.manager = manager;

        const programUserCollection: IProgramUser[] = [{ id: 74688 }];
        spyOn(programUserService, 'query').and.returnValue(of(new HttpResponse({ body: programUserCollection })));
        const additionalProgramUsers = [manager];
        const expectedCollection: IProgramUser[] = [...additionalProgramUsers, ...programUserCollection];
        spyOn(programUserService, 'addProgramUserToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        expect(programUserService.query).toHaveBeenCalled();
        expect(programUserService.addProgramUserToCollectionIfMissing).toHaveBeenCalledWith(
          programUserCollection,
          ...additionalProgramUsers
        );
        expect(comp.programUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call WorkLocation query and add missing value', () => {
        const programUser: IProgramUser = { id: 456 };
        const location: IWorkLocation = { id: 24662 };
        programUser.location = location;

        const workLocationCollection: IWorkLocation[] = [{ id: 50985 }];
        spyOn(workLocationService, 'query').and.returnValue(of(new HttpResponse({ body: workLocationCollection })));
        const additionalWorkLocations = [location];
        const expectedCollection: IWorkLocation[] = [...additionalWorkLocations, ...workLocationCollection];
        spyOn(workLocationService, 'addWorkLocationToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        expect(workLocationService.query).toHaveBeenCalled();
        expect(workLocationService.addWorkLocationToCollectionIfMissing).toHaveBeenCalledWith(
          workLocationCollection,
          ...additionalWorkLocations
        );
        expect(comp.workLocationsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const programUser: IProgramUser = { id: 456 };
        const client: IProgram = { id: 71977 };
        programUser.client = client;
        const manager: IProgramUser = { id: 11303 };
        programUser.manager = manager;
        const location: IWorkLocation = { id: 25375 };
        programUser.location = location;

        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(programUser));
        expect(comp.programsSharedCollection).toContain(client);
        expect(comp.programUsersSharedCollection).toContain(manager);
        expect(comp.workLocationsSharedCollection).toContain(location);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const programUser = { id: 123 };
        spyOn(programUserService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: programUser }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(programUserService.update).toHaveBeenCalledWith(programUser);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const programUser = new ProgramUser();
        spyOn(programUserService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: programUser }));
        saveSubject.complete();

        // THEN
        expect(programUserService.create).toHaveBeenCalledWith(programUser);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const programUser = { id: 123 };
        spyOn(programUserService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ programUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(programUserService.update).toHaveBeenCalledWith(programUser);
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

      describe('trackProgramUserById', () => {
        it('Should return tracked ProgramUser primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProgramUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackWorkLocationById', () => {
        it('Should return tracked WorkLocation primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackWorkLocationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
