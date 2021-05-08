jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FoundationalDataService } from '../service/foundational-data.service';
import { IFoundationalData, FoundationalData } from '../foundational-data.model';
import { IFoundationalDataType } from 'app/entities/foundational-data-type/foundational-data-type.model';
import { FoundationalDataTypeService } from 'app/entities/foundational-data-type/service/foundational-data-type.service';
import { IProgramUser } from 'app/entities/program-user/program-user.model';
import { ProgramUserService } from 'app/entities/program-user/service/program-user.service';

import { FoundationalDataUpdateComponent } from './foundational-data-update.component';

describe('Component Tests', () => {
  describe('FoundationalData Management Update Component', () => {
    let comp: FoundationalDataUpdateComponent;
    let fixture: ComponentFixture<FoundationalDataUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let foundationalDataService: FoundationalDataService;
    let foundationalDataTypeService: FoundationalDataTypeService;
    let programUserService: ProgramUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FoundationalDataUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FoundationalDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoundationalDataUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      foundationalDataService = TestBed.inject(FoundationalDataService);
      foundationalDataTypeService = TestBed.inject(FoundationalDataTypeService);
      programUserService = TestBed.inject(ProgramUserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call FoundationalDataType query and add missing value', () => {
        const foundationalData: IFoundationalData = { id: 456 };
        const foundationalDataType: IFoundationalDataType = { id: 75642 };
        foundationalData.foundationalDataType = foundationalDataType;

        const foundationalDataTypeCollection: IFoundationalDataType[] = [{ id: 79197 }];
        spyOn(foundationalDataTypeService, 'query').and.returnValue(of(new HttpResponse({ body: foundationalDataTypeCollection })));
        const additionalFoundationalDataTypes = [foundationalDataType];
        const expectedCollection: IFoundationalDataType[] = [...additionalFoundationalDataTypes, ...foundationalDataTypeCollection];
        spyOn(foundationalDataTypeService, 'addFoundationalDataTypeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ foundationalData });
        comp.ngOnInit();

        expect(foundationalDataTypeService.query).toHaveBeenCalled();
        expect(foundationalDataTypeService.addFoundationalDataTypeToCollectionIfMissing).toHaveBeenCalledWith(
          foundationalDataTypeCollection,
          ...additionalFoundationalDataTypes
        );
        expect(comp.foundationalDataTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ProgramUser query and add missing value', () => {
        const foundationalData: IFoundationalData = { id: 456 };
        const managers: IProgramUser[] = [{ id: 65690 }];
        foundationalData.managers = managers;

        const programUserCollection: IProgramUser[] = [{ id: 78998 }];
        spyOn(programUserService, 'query').and.returnValue(of(new HttpResponse({ body: programUserCollection })));
        const additionalProgramUsers = [...managers];
        const expectedCollection: IProgramUser[] = [...additionalProgramUsers, ...programUserCollection];
        spyOn(programUserService, 'addProgramUserToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ foundationalData });
        comp.ngOnInit();

        expect(programUserService.query).toHaveBeenCalled();
        expect(programUserService.addProgramUserToCollectionIfMissing).toHaveBeenCalledWith(
          programUserCollection,
          ...additionalProgramUsers
        );
        expect(comp.programUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const foundationalData: IFoundationalData = { id: 456 };
        const foundationalDataType: IFoundationalDataType = { id: 43863 };
        foundationalData.foundationalDataType = foundationalDataType;
        const managers: IProgramUser = { id: 23778 };
        foundationalData.managers = [managers];

        activatedRoute.data = of({ foundationalData });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(foundationalData));
        expect(comp.foundationalDataTypesSharedCollection).toContain(foundationalDataType);
        expect(comp.programUsersSharedCollection).toContain(managers);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const foundationalData = { id: 123 };
        spyOn(foundationalDataService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ foundationalData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: foundationalData }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(foundationalDataService.update).toHaveBeenCalledWith(foundationalData);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const foundationalData = new FoundationalData();
        spyOn(foundationalDataService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ foundationalData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: foundationalData }));
        saveSubject.complete();

        // THEN
        expect(foundationalDataService.create).toHaveBeenCalledWith(foundationalData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const foundationalData = { id: 123 };
        spyOn(foundationalDataService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ foundationalData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(foundationalDataService.update).toHaveBeenCalledWith(foundationalData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackFoundationalDataTypeById', () => {
        it('Should return tracked FoundationalDataType primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFoundationalDataTypeById(0, entity);
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
