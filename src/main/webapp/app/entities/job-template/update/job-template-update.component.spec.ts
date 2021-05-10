jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { JobTemplateService } from '../service/job-template.service';
import { IJobTemplate, JobTemplate } from '../job-template.model';
import { IJobCategoryTitle } from 'app/entities/job-category-title/job-category-title.model';
import { JobCategoryTitleService } from 'app/entities/job-category-title/service/job-category-title.service';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';
import { HierarchyService } from 'app/entities/hierarchy/service/hierarchy.service';

import { JobTemplateUpdateComponent } from './job-template-update.component';

describe('Component Tests', () => {
  describe('JobTemplate Management Update Component', () => {
    let comp: JobTemplateUpdateComponent;
    let fixture: ComponentFixture<JobTemplateUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let jobTemplateService: JobTemplateService;
    let jobCategoryTitleService: JobCategoryTitleService;
    let hierarchyService: HierarchyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [JobTemplateUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(JobTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobTemplateUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      jobTemplateService = TestBed.inject(JobTemplateService);
      jobCategoryTitleService = TestBed.inject(JobCategoryTitleService);
      hierarchyService = TestBed.inject(HierarchyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call JobCategoryTitle query and add missing value', () => {
        const jobTemplate: IJobTemplate = { id: 456 };
        const category: IJobCategoryTitle = { id: 78634 };
        jobTemplate.category = category;

        const jobCategoryTitleCollection: IJobCategoryTitle[] = [{ id: 36356 }];
        spyOn(jobCategoryTitleService, 'query').and.returnValue(of(new HttpResponse({ body: jobCategoryTitleCollection })));
        const additionalJobCategoryTitles = [category];
        const expectedCollection: IJobCategoryTitle[] = [...additionalJobCategoryTitles, ...jobCategoryTitleCollection];
        spyOn(jobCategoryTitleService, 'addJobCategoryTitleToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jobTemplate });
        comp.ngOnInit();

        expect(jobCategoryTitleService.query).toHaveBeenCalled();
        expect(jobCategoryTitleService.addJobCategoryTitleToCollectionIfMissing).toHaveBeenCalledWith(
          jobCategoryTitleCollection,
          ...additionalJobCategoryTitles
        );
        expect(comp.jobCategoryTitlesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Hierarchy query and add missing value', () => {
        const jobTemplate: IJobTemplate = { id: 456 };
        const hierarchy: IHierarchy = { id: 41216 };
        jobTemplate.hierarchy = hierarchy;

        const hierarchyCollection: IHierarchy[] = [{ id: 37199 }];
        spyOn(hierarchyService, 'query').and.returnValue(of(new HttpResponse({ body: hierarchyCollection })));
        const additionalHierarchies = [hierarchy];
        const expectedCollection: IHierarchy[] = [...additionalHierarchies, ...hierarchyCollection];
        spyOn(hierarchyService, 'addHierarchyToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ jobTemplate });
        comp.ngOnInit();

        expect(hierarchyService.query).toHaveBeenCalled();
        expect(hierarchyService.addHierarchyToCollectionIfMissing).toHaveBeenCalledWith(hierarchyCollection, ...additionalHierarchies);
        expect(comp.hierarchiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const jobTemplate: IJobTemplate = { id: 456 };
        const category: IJobCategoryTitle = { id: 11532 };
        jobTemplate.category = category;
        const hierarchy: IHierarchy = { id: 20864 };
        jobTemplate.hierarchy = hierarchy;

        activatedRoute.data = of({ jobTemplate });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(jobTemplate));
        expect(comp.jobCategoryTitlesSharedCollection).toContain(category);
        expect(comp.hierarchiesSharedCollection).toContain(hierarchy);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobTemplate = { id: 123 };
        spyOn(jobTemplateService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobTemplate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobTemplate }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(jobTemplateService.update).toHaveBeenCalledWith(jobTemplate);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobTemplate = new JobTemplate();
        spyOn(jobTemplateService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobTemplate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: jobTemplate }));
        saveSubject.complete();

        // THEN
        expect(jobTemplateService.create).toHaveBeenCalledWith(jobTemplate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const jobTemplate = { id: 123 };
        spyOn(jobTemplateService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ jobTemplate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(jobTemplateService.update).toHaveBeenCalledWith(jobTemplate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackJobCategoryTitleById', () => {
        it('Should return tracked JobCategoryTitle primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackJobCategoryTitleById(0, entity);
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
    });
  });
});
