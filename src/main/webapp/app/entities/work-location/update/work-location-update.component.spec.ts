jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { WorkLocationService } from '../service/work-location.service';
import { IWorkLocation, WorkLocation } from '../work-location.model';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';

import { WorkLocationUpdateComponent } from './work-location-update.component';

describe('Component Tests', () => {
  describe('WorkLocation Management Update Component', () => {
    let comp: WorkLocationUpdateComponent;
    let fixture: ComponentFixture<WorkLocationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let workLocationService: WorkLocationService;
    let programService: ProgramService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WorkLocationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(WorkLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkLocationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      workLocationService = TestBed.inject(WorkLocationService);
      programService = TestBed.inject(ProgramService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Program query and add missing value', () => {
        const workLocation: IWorkLocation = { id: 456 };
        const client: IProgram = { id: 61085 };
        workLocation.client = client;

        const programCollection: IProgram[] = [{ id: 26407 }];
        spyOn(programService, 'query').and.returnValue(of(new HttpResponse({ body: programCollection })));
        const additionalPrograms = [client];
        const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
        spyOn(programService, 'addProgramToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ workLocation });
        comp.ngOnInit();

        expect(programService.query).toHaveBeenCalled();
        expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(programCollection, ...additionalPrograms);
        expect(comp.programsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const workLocation: IWorkLocation = { id: 456 };
        const client: IProgram = { id: 24124 };
        workLocation.client = client;

        activatedRoute.data = of({ workLocation });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(workLocation));
        expect(comp.programsSharedCollection).toContain(client);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const workLocation = { id: 123 };
        spyOn(workLocationService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ workLocation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: workLocation }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(workLocationService.update).toHaveBeenCalledWith(workLocation);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const workLocation = new WorkLocation();
        spyOn(workLocationService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ workLocation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: workLocation }));
        saveSubject.complete();

        // THEN
        expect(workLocationService.create).toHaveBeenCalledWith(workLocation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const workLocation = { id: 123 };
        spyOn(workLocationService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ workLocation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(workLocationService.update).toHaveBeenCalledWith(workLocation);
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
    });
  });
});
