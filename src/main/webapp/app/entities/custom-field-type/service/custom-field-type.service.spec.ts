import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICustomFieldType, CustomFieldType } from '../custom-field-type.model';

import { CustomFieldTypeService } from './custom-field-type.service';

describe('Service Tests', () => {
  describe('CustomFieldType Service', () => {
    let service: CustomFieldTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomFieldType;
    let expectedResult: ICustomFieldType | ICustomFieldType[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CustomFieldTypeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        code: 'AAAAAAA',
        description: 'AAAAAAA',
        isActive: false,
        createdBy: 'AAAAAAA',
        createdAt: currentDate,
        updatedBy: 'AAAAAAA',
        updatedAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CustomFieldType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new CustomFieldType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomFieldType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            code: 'BBBBBB',
            description: 'BBBBBB',
            isActive: true,
            createdBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
            updatedBy: 'BBBBBB',
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CustomFieldType', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          new CustomFieldType()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CustomFieldType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            code: 'BBBBBB',
            description: 'BBBBBB',
            isActive: true,
            createdBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
            updatedBy: 'BBBBBB',
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CustomFieldType', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCustomFieldTypeToCollectionIfMissing', () => {
        it('should add a CustomFieldType to an empty array', () => {
          const customFieldType: ICustomFieldType = { id: 123 };
          expectedResult = service.addCustomFieldTypeToCollectionIfMissing([], customFieldType);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customFieldType);
        });

        it('should not add a CustomFieldType to an array that contains it', () => {
          const customFieldType: ICustomFieldType = { id: 123 };
          const customFieldTypeCollection: ICustomFieldType[] = [
            {
              ...customFieldType,
            },
            { id: 456 },
          ];
          expectedResult = service.addCustomFieldTypeToCollectionIfMissing(customFieldTypeCollection, customFieldType);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CustomFieldType to an array that doesn't contain it", () => {
          const customFieldType: ICustomFieldType = { id: 123 };
          const customFieldTypeCollection: ICustomFieldType[] = [{ id: 456 }];
          expectedResult = service.addCustomFieldTypeToCollectionIfMissing(customFieldTypeCollection, customFieldType);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customFieldType);
        });

        it('should add only unique CustomFieldType to an array', () => {
          const customFieldTypeArray: ICustomFieldType[] = [{ id: 123 }, { id: 456 }, { id: 76356 }];
          const customFieldTypeCollection: ICustomFieldType[] = [{ id: 123 }];
          expectedResult = service.addCustomFieldTypeToCollectionIfMissing(customFieldTypeCollection, ...customFieldTypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const customFieldType: ICustomFieldType = { id: 123 };
          const customFieldType2: ICustomFieldType = { id: 456 };
          expectedResult = service.addCustomFieldTypeToCollectionIfMissing([], customFieldType, customFieldType2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customFieldType);
          expect(expectedResult).toContain(customFieldType2);
        });

        it('should accept null and undefined values', () => {
          const customFieldType: ICustomFieldType = { id: 123 };
          expectedResult = service.addCustomFieldTypeToCollectionIfMissing([], null, customFieldType, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customFieldType);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
