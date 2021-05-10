jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomFieldTypeService } from '../service/custom-field-type.service';
import { ICustomFieldType, CustomFieldType } from '../custom-field-type.model';
import { IFoundationalDataType } from 'app/entities/foundational-data-type/foundational-data-type.model';
import { FoundationalDataTypeService } from 'app/entities/foundational-data-type/service/foundational-data-type.service';

import { CustomFieldTypeUpdateComponent } from './custom-field-type-update.component';

describe('Component Tests', () => {
  describe('CustomFieldType Management Update Component', () => {
    let comp: CustomFieldTypeUpdateComponent;
    let fixture: ComponentFixture<CustomFieldTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let customFieldTypeService: CustomFieldTypeService;
    let foundationalDataTypeService: FoundationalDataTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomFieldTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CustomFieldTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomFieldTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      customFieldTypeService = TestBed.inject(CustomFieldTypeService);
      foundationalDataTypeService = TestBed.inject(FoundationalDataTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call FoundationalDataType query and add missing value', () => {
        const customFieldType: ICustomFieldType = { id: 456 };
        const foundationalDataType: IFoundationalDataType = { id: 26703 };
        customFieldType.foundationalDataType = foundationalDataType;

        const foundationalDataTypeCollection: IFoundationalDataType[] = [{ id: 95907 }];
        spyOn(foundationalDataTypeService, 'query').and.returnValue(of(new HttpResponse({ body: foundationalDataTypeCollection })));
        const additionalFoundationalDataTypes = [foundationalDataType];
        const expectedCollection: IFoundationalDataType[] = [...additionalFoundationalDataTypes, ...foundationalDataTypeCollection];
        spyOn(foundationalDataTypeService, 'addFoundationalDataTypeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ customFieldType });
        comp.ngOnInit();

        expect(foundationalDataTypeService.query).toHaveBeenCalled();
        expect(foundationalDataTypeService.addFoundationalDataTypeToCollectionIfMissing).toHaveBeenCalledWith(
          foundationalDataTypeCollection,
          ...additionalFoundationalDataTypes
        );
        expect(comp.foundationalDataTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const customFieldType: ICustomFieldType = { id: 456 };
        const foundationalDataType: IFoundationalDataType = { id: 78528 };
        customFieldType.foundationalDataType = foundationalDataType;

        activatedRoute.data = of({ customFieldType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(customFieldType));
        expect(comp.foundationalDataTypesSharedCollection).toContain(foundationalDataType);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const customFieldType = { id: 123 };
        spyOn(customFieldTypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ customFieldType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customFieldType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(customFieldTypeService.update).toHaveBeenCalledWith(customFieldType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const customFieldType = new CustomFieldType();
        spyOn(customFieldTypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ customFieldType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customFieldType }));
        saveSubject.complete();

        // THEN
        expect(customFieldTypeService.create).toHaveBeenCalledWith(customFieldType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const customFieldType = { id: 123 };
        spyOn(customFieldTypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ customFieldType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(customFieldTypeService.update).toHaveBeenCalledWith(customFieldType);
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
    });
  });
});
