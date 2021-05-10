jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { JobTemplateRateCardService } from '../service/job-template-rate-card.service';
import { IJobTemplateRateCard, JobTemplateRateCard } from '../job-template-rate-card.model';
import { IJobTemplate } from 'app/entities/job-template/job-template.model';
import { JobTemplateService } from 'app/entities/job-template/service/job-template.service';
import { IJobCategoryTitle } from 'app/entities/job-category-title/job-category-title.model';
import { JobCategoryTitleService } from 'app/entities/job-category-title/service/job-category-title.service';

import { JobTemplateRateCardUpdateComponent } from './job-template-rate-card-update.component';

describe('Component Tests', () => {
  describe('JobTemplateRateCard Management Update Component', () => {
    let comp: JobTemplateRateCardUpdateComponent;
    let fixture: ComponentFixture<JobTemplateRateCardUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let jobTemplateRateCardService: JobTemplateRateCardService;
    let jobTemplateService: JobTemplateService;
    let jobCategoryTitleService: JobCategoryTitleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [JobTemplateRateCardUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(JobTemplateRateCardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobTemplateRateCardUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      jobTemplateRateCardService = TestBed.inject(JobTemplateRateCardService);
      jobTemplateService = TestBed.inject(JobTemplateService);
      jobCategoryTitleService = TestBed.inject(JobCategoryTitleService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call JobTemplate query and add missing value', () => {
        const jobTemplateRateCard: IJobTemplateRateCard = { id: 456 };
        const template: IJobTemplate = { id: 80334 };
        jobTemplateRateCard.template = template;

        const jobTemplateCollection: IJobTemplate[] = [{ id: 83827 }];
        spyOn(jobTemplateService, 'query').and.returnValue(of(new HttpResponse({ body: jobTemplateCollection })));
        const additionalJobTemplates = [template];
        const expectedCollection: IJobTemplate[] = [...additionalJobTemplates, ...jobTemplateCollection];
        spyOn(jobTemplateService, 'addJobTemplateToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jobTemplateRateCard });
        comp.ngOnInit();

        expect(jobTemplateService.query).toHaveBeenCalled();
        expect(jobTemplateService.addJobTemplateToCollectionIfMissing).toHaveBeenCalledWith(
          jobTemplateCollection,
          ...additionalJobTemplates
        );
        expect(comp.jobTemplatesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call JobCategoryTitle query and add missing value', () => {
        const jobTemplateRateCard: IJobTemplateRateCard = { id: 456 };
        const category: IJobCategoryTitle = { id: 72115 };
        jobTemplateRateCard.category = category;

        const jobCategoryTitleCollection: IJobCategoryTitle[] = [{ id: 51766 }];
        spyOn(jobCategoryTitleService, 'query').and.returnValue(of(new HttpResponse({ body: jobCategoryTitleCollection })));
        const additionalJobCategoryTitles = [category];
        const expectedCollection: IJobCategoryTitle[] = [...additionalJobCategoryTitles, ...jobCategoryTitleCollection];
        spyOn(jobCategoryTitleService, 'addJobCategoryTitleToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jobTemplateRateCard });
        comp.ngOnInit();

        expect(jobCategoryTitleService.query).toHaveBeenCalled();
        expect(jobCategoryTitleService.addJobCategoryTitleToCollectionIfMissing).toHaveBeenCalledWith(
          jobCategoryTitleCollection,
          ...additionalJobCategoryTitles
        );
        expect(comp.jobCategoryTitlesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const jobTemplateRateCard: IJobTemplateRateCard = { id: 456 };
        const template: IJobTemplate = { id: 16937 };
        jobTemplateRateCard.template = template;
        const category: IJobCategoryTitle = { id: 21596 };
        jobTemplateRateCard.category = category;

        activatedRoute.data = of({ jobTemplateRateCard });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(jobTemplateRateCard));
        expect(comp.jobTemplatesSharedCollection).toContain(template);
        expect(comp.jobCategoryTitlesSharedCollection).toContain(category);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobTemplateRateCard = { id: 123 };
        spyOn(jobTemplateRateCardService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobTemplateRateCard });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobTemplateRateCard }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(jobTemplateRateCardService.update).toHaveBeenCalledWith(jobTemplateRateCard);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobTemplateRateCard = new JobTemplateRateCard();
        spyOn(jobTemplateRateCardService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobTemplateRateCard });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobTemplateRateCard }));
        saveSubject.complete();

        // THEN
        expect(jobTemplateRateCardService.create).toHaveBeenCalledWith(jobTemplateRateCard);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobTemplateRateCard = { id: 123 };
        spyOn(jobTemplateRateCardService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobTemplateRateCard });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(jobTemplateRateCardService.update).toHaveBeenCalledWith(jobTemplateRateCard);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackJobTemplateById', () => {
        it('Should return tracked JobTemplate primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackJobTemplateById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackJobCategoryTitleById', () => {
        it('Should return tracked JobCategoryTitle primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackJobCategoryTitleById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
