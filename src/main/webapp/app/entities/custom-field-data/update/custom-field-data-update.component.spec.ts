jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CustomFieldDataService } from '../service/custom-field-data.service';
import { ICustomFieldData, CustomFieldData } from '../custom-field-data.model';
import { IFoundationalData } from 'app/entities/foundational-data/foundational-data.model';
import { FoundationalDataService } from 'app/entities/foundational-data/service/foundational-data.service';
import { ICustomFieldType } from 'app/entities/custom-field-type/custom-field-type.model';
import { CustomFieldTypeService } from 'app/entities/custom-field-type/service/custom-field-type.service';

import { CustomFieldDataUpdateComponent } from './custom-field-data-update.component';

describe('Component Tests', () => {
  describe('CustomFieldData Management Update Component', () => {
    let comp: CustomFieldDataUpdateComponent;
    let fixture: ComponentFixture<CustomFieldDataUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let customFieldDataService: CustomFieldDataService;
    let foundationalDataService: FoundationalDataService;
    let customFieldTypeService: CustomFieldTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomFieldDataUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CustomFieldDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomFieldDataUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      customFieldDataService = TestBed.inject(CustomFieldDataService);
      foundationalDataService = TestBed.inject(FoundationalDataService);
      customFieldTypeService = TestBed.inject(CustomFieldTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call FoundationalData query and add missing value', () => {
        const customFieldData: ICustomFieldData = { id: 456 };
        const foundationalData: IFoundationalData = { id: 20717 };
        customFieldData.foundationalData = foundationalData;

        const foundationalDataCollection: IFoundationalData[] = [{ id: 58958 }];
        spyOn(foundationalDataService, 'query').and.returnValue(of(new HttpResponse({ body: foundationalDataCollection })));
        const additionalFoundationalData = [foundationalData];
        const expectedCollection: IFoundationalData[] = [...additionalFoundationalData, ...foundationalDataCollection];
        spyOn(foundationalDataService, 'addFoundationalDataToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ customFieldData });
        comp.ngOnInit();

        expect(foundationalDataService.query).toHaveBeenCalled();
        expect(foundationalDataService.addFoundationalDataToCollectionIfMissing).toHaveBeenCalledWith(
          foundationalDataCollection,
          ...additionalFoundationalData
        );
        expect(comp.foundationalDataSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CustomFieldType query and add missing value', () => {
        const customFieldData: ICustomFieldData = { id: 456 };
        const customFieldType: ICustomFieldType = { id: 24527 };
        customFieldData.customFieldType = customFieldType;

        const customFieldTypeCollection: ICustomFieldType[] = [{ id: 73426 }];
        spyOn(customFieldTypeService, 'query').and.returnValue(of(new HttpResponse({ body: customFieldTypeCollection })));
        const additionalCustomFieldTypes = [customFieldType];
        const expectedCollection: ICustomFieldType[] = [...additionalCustomFieldTypes, ...customFieldTypeCollection];
        spyOn(customFieldTypeService, 'addCustomFieldTypeToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ customFieldData });
        comp.ngOnInit();

        expect(customFieldTypeService.query).toHaveBeenCalled();
        expect(customFieldTypeService.addCustomFieldTypeToCollectionIfMissing).toHaveBeenCalledWith(
          customFieldTypeCollection,
          ...additionalCustomFieldTypes
        );
        expect(comp.customFieldTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const customFieldData: ICustomFieldData = { id: 456 };
        const foundationalData: IFoundationalData = { id: 29690 };
        customFieldData.foundationalData = foundationalData;
        const customFieldType: ICustomFieldType = { id: 78098 };
        customFieldData.customFieldType = customFieldType;

        activatedRoute.data = of({ customFieldData });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(customFieldData));
        expect(comp.foundationalDataSharedCollection).toContain(foundationalData);
        expect(comp.customFieldTypesSharedCollection).toContain(customFieldType);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const customFieldData = { id: 123 };
        spyOn(customFieldDataService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ customFieldData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customFieldData }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(customFieldDataService.update).toHaveBeenCalledWith(customFieldData);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const customFieldData = new CustomFieldData();
        spyOn(customFieldDataService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ customFieldData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: customFieldData }));
        saveSubject.complete();

        // THEN
        expect(customFieldDataService.create).toHaveBeenCalledWith(customFieldData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const customFieldData = { id: 123 };
        spyOn(customFieldDataService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ customFieldData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(customFieldDataService.update).toHaveBeenCalledWith(customFieldData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackFoundationalDataById', () => {
        it('Should return tracked FoundationalData primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFoundationalDataById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCustomFieldTypeById', () => {
        it('Should return tracked CustomFieldType primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCustomFieldTypeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
