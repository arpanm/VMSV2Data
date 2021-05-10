import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICustomFieldData, CustomFieldData } from '../custom-field-data.model';

import { CustomFieldDataService } from './custom-field-data.service';

describe('Service Tests', () => {
  describe('CustomFieldData Service', () => {
    let service: CustomFieldDataService;
    let httpMock: HttpTestingController;
    let elemDefault: ICustomFieldData;
    let expectedResult: ICustomFieldData | ICustomFieldData[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CustomFieldDataService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        value: 'AAAAAAA',
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

      it('should create a CustomFieldData', () => {
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

        service.create(new CustomFieldData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CustomFieldData', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            value: 'BBBBBB',
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

      it('should partial update a CustomFieldData', () => {
        const patchObject = Object.assign(
          {
            isActive: true,
            createdBy: 'BBBBBB',
            updatedBy: 'BBBBBB',
          },
          new CustomFieldData()
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

      it('should return a list of CustomFieldData', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            value: 'BBBBBB',
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

      it('should delete a CustomFieldData', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCustomFieldDataToCollectionIfMissing', () => {
        it('should add a CustomFieldData to an empty array', () => {
          const customFieldData: ICustomFieldData = { id: 123 };
          expectedResult = service.addCustomFieldDataToCollectionIfMissing([], customFieldData);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customFieldData);
        });

        it('should not add a CustomFieldData to an array that contains it', () => {
          const customFieldData: ICustomFieldData = { id: 123 };
          const customFieldDataCollection: ICustomFieldData[] = [
            {
              ...customFieldData,
            },
            { id: 456 },
          ];
          expectedResult = service.addCustomFieldDataToCollectionIfMissing(customFieldDataCollection, customFieldData);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CustomFieldData to an array that doesn't contain it", () => {
          const customFieldData: ICustomFieldData = { id: 123 };
          const customFieldDataCollection: ICustomFieldData[] = [{ id: 456 }];
          expectedResult = service.addCustomFieldDataToCollectionIfMissing(customFieldDataCollection, customFieldData);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customFieldData);
        });

        it('should add only unique CustomFieldData to an array', () => {
          const customFieldDataArray: ICustomFieldData[] = [{ id: 123 }, { id: 456 }, { id: 1508 }];
          const customFieldDataCollection: ICustomFieldData[] = [{ id: 123 }];
          expectedResult = service.addCustomFieldDataToCollectionIfMissing(customFieldDataCollection, ...customFieldDataArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const customFieldData: ICustomFieldData = { id: 123 };
          const customFieldData2: ICustomFieldData = { id: 456 };
          expectedResult = service.addCustomFieldDataToCollectionIfMissing([], customFieldData, customFieldData2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(customFieldData);
          expect(expectedResult).toContain(customFieldData2);
        });

        it('should accept null and undefined values', () => {
          const customFieldData: ICustomFieldData = { id: 123 };
          expectedResult = service.addCustomFieldDataToCollectionIfMissing([], null, customFieldData, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(customFieldData);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
