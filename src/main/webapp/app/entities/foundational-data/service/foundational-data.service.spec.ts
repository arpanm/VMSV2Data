import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFoundationalData, FoundationalData } from '../foundational-data.model';

import { FoundationalDataService } from './foundational-data.service';

describe('Service Tests', () => {
  describe('FoundationalData Service', () => {
    let service: FoundationalDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IFoundationalData;
    let expectedResult: IFoundationalData | IFoundationalData[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FoundationalDataService);
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

      it('should create a FoundationalData', () => {
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

        service.create(new FoundationalData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FoundationalData', () => {
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

      it('should partial update a FoundationalData', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            createdBy: 'BBBBBB',
            updatedBy: 'BBBBBB',
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          new FoundationalData()
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

      it('should return a list of FoundationalData', () => {
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

      it('should delete a FoundationalData', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFoundationalDataToCollectionIfMissing', () => {
        it('should add a FoundationalData to an empty array', () => {
          const foundationalData: IFoundationalData = { id: 123 };
          expectedResult = service.addFoundationalDataToCollectionIfMissing([], foundationalData);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(foundationalData);
        });

        it('should not add a FoundationalData to an array that contains it', () => {
          const foundationalData: IFoundationalData = { id: 123 };
          const foundationalDataCollection: IFoundationalData[] = [
            {
              ...foundationalData,
            },
            { id: 456 },
          ];
          expectedResult = service.addFoundationalDataToCollectionIfMissing(foundationalDataCollection, foundationalData);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FoundationalData to an array that doesn't contain it", () => {
          const foundationalData: IFoundationalData = { id: 123 };
          const foundationalDataCollection: IFoundationalData[] = [{ id: 456 }];
          expectedResult = service.addFoundationalDataToCollectionIfMissing(foundationalDataCollection, foundationalData);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(foundationalData);
        });

        it('should add only unique FoundationalData to an array', () => {
          const foundationalDataArray: IFoundationalData[] = [{ id: 123 }, { id: 456 }, { id: 40184 }];
          const foundationalDataCollection: IFoundationalData[] = [{ id: 123 }];
          expectedResult = service.addFoundationalDataToCollectionIfMissing(foundationalDataCollection, ...foundationalDataArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const foundationalData: IFoundationalData = { id: 123 };
          const foundationalData2: IFoundationalData = { id: 456 };
          expectedResult = service.addFoundationalDataToCollectionIfMissing([], foundationalData, foundationalData2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(foundationalData);
          expect(expectedResult).toContain(foundationalData2);
        });

        it('should accept null and undefined values', () => {
          const foundationalData: IFoundationalData = { id: 123 };
          expectedResult = service.addFoundationalDataToCollectionIfMissing([], null, foundationalData, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(foundationalData);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
