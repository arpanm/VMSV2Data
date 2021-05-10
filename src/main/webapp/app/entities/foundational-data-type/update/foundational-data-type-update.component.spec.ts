jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FoundationalDataTypeService } from '../service/foundational-data-type.service';
import { IFoundationalDataType, FoundationalDataType } from '../foundational-data-type.model';
import { IHierarchy } from 'app/entities/hierarchy/hierarchy.model';
import { HierarchyService } from 'app/entities/hierarchy/service/hierarchy.service';

import { FoundationalDataTypeUpdateComponent } from './foundational-data-type-update.component';

describe('Component Tests', () => {
  describe('FoundationalDataType Management Update Component', () => {
    let comp: FoundationalDataTypeUpdateComponent;
    let fixture: ComponentFixture<FoundationalDataTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let foundationalDataTypeService: FoundationalDataTypeService;
    let hierarchyService: HierarchyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FoundationalDataTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FoundationalDataTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoundationalDataTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      foundationalDataTypeService = TestBed.inject(FoundationalDataTypeService);
      hierarchyService = TestBed.inject(HierarchyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Hierarchy query and add missing value', () => {
        const foundationalDataType: IFoundationalDataType = { id: 456 };
        const hierarchy: IHierarchy = { id: 14763 };
        foundationalDataType.hierarchy = hierarchy;

        const hierarchyCollection: IHierarchy[] = [{ id: 99932 }];
        spyOn(hierarchyService, 'query').and.returnValue(of(new HttpResponse({ body: hierarchyCollection })));
        const additionalHierarchies = [hierarchy];
        const expectedCollection: IHierarchy[] = [...additionalHierarchies, ...hierarchyCollection];
        spyOn(hierarchyService, 'addHierarchyToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ foundationalDataType });
        comp.ngOnInit();

        expect(hierarchyService.query).toHaveBeenCalled();
        expect(hierarchyService.addHierarchyToCollectionIfMissing).toHaveBeenCalledWith(hierarchyCollection, ...additionalHierarchies);
        expect(comp.hierarchiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const foundationalDataType: IFoundationalDataType = { id: 456 };
        const hierarchy: IHierarchy = { id: 72570 };
        foundationalDataType.hierarchy = hierarchy;

        activatedRoute.data = of({ foundationalDataType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(foundationalDataType));
        expect(comp.hierarchiesSharedCollection).toContain(hierarchy);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const foundationalDataType = { id: 123 };
        spyOn(foundationalDataTypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ foundationalDataType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: foundationalDataType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(foundationalDataTypeService.update).toHaveBeenCalledWith(foundationalDataType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const foundationalDataType = new FoundationalDataType();
        spyOn(foundationalDataTypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ foundationalDataType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: foundationalDataType }));
        saveSubject.complete();

        // THEN
        expect(foundationalDataTypeService.create).toHaveBeenCalledWith(foundationalDataType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const foundationalDataType = { id: 123 };
        spyOn(foundationalDataTypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ foundationalDataType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(foundationalDataTypeService.update).toHaveBeenCalledWith(foundationalDataType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
