jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProgramService } from '../service/program.service';
import { IProgram, Program } from '../program.model';

import { ProgramUpdateComponent } from './program-update.component';

describe('Component Tests', () => {
  describe('Program Management Update Component', () => {
    let comp: ProgramUpdateComponent;
    let fixture: ComponentFixture<ProgramUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let programService: ProgramService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProgramUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProgramUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      programService = TestBed.inject(ProgramService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const program: IProgram = { id: 456 };

        activatedRoute.data = of({ program });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(program));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const program = { id: 123 };
        spyOn(programService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ program });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: program }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(programService.update).toHaveBeenCalledWith(program);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const program = new Program();
        spyOn(programService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ program });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: program }));
        saveSubject.complete();

        // THEN
        expect(programService.create).toHaveBeenCalledWith(program);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const program = { id: 123 };
        spyOn(programService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ program });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(programService.update).toHaveBeenCalledWith(program);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
