jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { JobCategoryTitleService } from '../service/job-category-title.service';
import { IJobCategoryTitle, JobCategoryTitle } from '../job-category-title.model';

import { JobCategoryTitleUpdateComponent } from './job-category-title-update.component';

describe('Component Tests', () => {
  describe('JobCategoryTitle Management Update Component', () => {
    let comp: JobCategoryTitleUpdateComponent;
    let fixture: ComponentFixture<JobCategoryTitleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let jobCategoryTitleService: JobCategoryTitleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [JobCategoryTitleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(JobCategoryTitleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobCategoryTitleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      jobCategoryTitleService = TestBed.inject(JobCategoryTitleService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const jobCategoryTitle: IJobCategoryTitle = { id: 456 };

        activatedRoute.data = of({ jobCategoryTitle });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(jobCategoryTitle));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobCategoryTitle = { id: 123 };
        spyOn(jobCategoryTitleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobCategoryTitle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobCategoryTitle }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(jobCategoryTitleService.update).toHaveBeenCalledWith(jobCategoryTitle);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobCategoryTitle = new JobCategoryTitle();
        spyOn(jobCategoryTitleService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobCategoryTitle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobCategoryTitle }));
        saveSubject.complete();

        // THEN
        expect(jobCategoryTitleService.create).toHaveBeenCalledWith(jobCategoryTitle);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobCategoryTitle = { id: 123 };
        spyOn(jobCategoryTitleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobCategoryTitle });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(jobCategoryTitleService.update).toHaveBeenCalledWith(jobCategoryTitle);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
