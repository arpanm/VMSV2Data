import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFoundationalDataType, FoundationalDataType } from '../foundational-data-type.model';

import { FoundationalDataTypeService } from './foundational-data-type.service';

describe('Service Tests', () => {
  describe('FoundationalDataType Service', () => {
    let service: FoundationalDataTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IFoundationalDataType;
    let expectedResult: IFoundationalDataType | IFoundationalDataType[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FoundationalDataTypeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        requiredInHierarchy: false,
        requiredInJobs: false,
        requiredInSow: false,
        fileUploadPath: 'AAAAAAA',
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

      it('should create a FoundationalDataType', () => {
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

        service.create(new FoundationalDataType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FoundationalDataType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            requiredInHierarchy: true,
            requiredInJobs: true,
            requiredInSow: true,
            fileUploadPath: 'BBBBBB',
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

      it('should partial update a FoundationalDataType', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            requiredInHierarchy: true,
            requiredInSow: true,
            fileUploadPath: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
            updatedBy: 'BBBBBB',
          },
          new FoundationalDataType()
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

      it('should return a list of FoundationalDataType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            requiredInHierarchy: true,
            requiredInJobs: true,
            requiredInSow: true,
            fileUploadPath: 'BBBBBB',
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

      it('should delete a FoundationalDataType', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFoundationalDataTypeToCollectionIfMissing', () => {
        it('should add a FoundationalDataType to an empty array', () => {
          const foundationalDataType: IFoundationalDataType = { id: 123 };
          expectedResult = service.addFoundationalDataTypeToCollectionIfMissing([], foundationalDataType);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(foundationalDataType);
        });

        it('should not add a FoundationalDataType to an array that contains it', () => {
          const foundationalDataType: IFoundationalDataType = { id: 123 };
          const foundationalDataTypeCollection: IFoundationalDataType[] = [
            {
              ...foundationalDataType,
            },
            { id: 456 },
          ];
          expectedResult = service.addFoundationalDataTypeToCollectionIfMissing(foundationalDataTypeCollection, foundationalDataType);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FoundationalDataType to an array that doesn't contain it", () => {
          const foundationalDataType: IFoundationalDataType = { id: 123 };
          const foundationalDataTypeCollection: IFoundationalDataType[] = [{ id: 456 }];
          expectedResult = service.addFoundationalDataTypeToCollectionIfMissing(foundationalDataTypeCollection, foundationalDataType);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(foundationalDataType);
        });

        it('should add only unique FoundationalDataType to an array', () => {
          const foundationalDataTypeArray: IFoundationalDataType[] = [{ id: 123 }, { id: 456 }, { id: 92772 }];
          const foundationalDataTypeCollection: IFoundationalDataType[] = [{ id: 123 }];
          expectedResult = service.addFoundationalDataTypeToCollectionIfMissing(
            foundationalDataTypeCollection,
            ...foundationalDataTypeArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const foundationalDataType: IFoundationalDataType = { id: 123 };
          const foundationalDataType2: IFoundationalDataType = { id: 456 };
          expectedResult = service.addFoundationalDataTypeToCollectionIfMissing([], foundationalDataType, foundationalDataType2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(foundationalDataType);
          expect(expectedResult).toContain(foundationalDataType2);
        });

        it('should accept null and undefined values', () => {
          const foundationalDataType: IFoundationalDataType = { id: 123 };
          expectedResult = service.addFoundationalDataTypeToCollectionIfMissing([], null, foundationalDataType, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(foundationalDataType);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
