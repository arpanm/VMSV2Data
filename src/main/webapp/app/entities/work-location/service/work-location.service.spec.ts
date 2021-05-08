import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Country } from 'app/entities/enumerations/country.model';
import { State } from 'app/entities/enumerations/state.model';
import { IWorkLocation, WorkLocation } from '../work-location.model';

import { WorkLocationService } from './work-location.service';

describe('Service Tests', () => {
  describe('WorkLocation Service', () => {
    let service: WorkLocationService;
    let httpMock: HttpTestingController;
    let elemDefault: IWorkLocation;
    let expectedResult: IWorkLocation | IWorkLocation[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(WorkLocationService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        country: Country.US,
        state: State.Alabama,
        address: 'AAAAAAA',
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

      it('should create a WorkLocation', () => {
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

        service.create(new WorkLocation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WorkLocation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            country: 'BBBBBB',
            state: 'BBBBBB',
            address: 'BBBBBB',
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

      it('should partial update a WorkLocation', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
            country: 'BBBBBB',
            state: 'BBBBBB',
            address: 'BBBBBB',
          },
          new WorkLocation()
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

      it('should return a list of WorkLocation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            name: 'BBBBBB',
            description: 'BBBBBB',
            country: 'BBBBBB',
            state: 'BBBBBB',
            address: 'BBBBBB',
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

      it('should delete a WorkLocation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addWorkLocationToCollectionIfMissing', () => {
        it('should add a WorkLocation to an empty array', () => {
          const workLocation: IWorkLocation = { id: 123 };
          expectedResult = service.addWorkLocationToCollectionIfMissing([], workLocation);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(workLocation);
        });

        it('should not add a WorkLocation to an array that contains it', () => {
          const workLocation: IWorkLocation = { id: 123 };
          const workLocationCollection: IWorkLocation[] = [
            {
              ...workLocation,
            },
            { id: 456 },
          ];
          expectedResult = service.addWorkLocationToCollectionIfMissing(workLocationCollection, workLocation);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a WorkLocation to an array that doesn't contain it", () => {
          const workLocation: IWorkLocation = { id: 123 };
          const workLocationCollection: IWorkLocation[] = [{ id: 456 }];
          expectedResult = service.addWorkLocationToCollectionIfMissing(workLocationCollection, workLocation);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(workLocation);
        });

        it('should add only unique WorkLocation to an array', () => {
          const workLocationArray: IWorkLocation[] = [{ id: 123 }, { id: 456 }, { id: 70394 }];
          const workLocationCollection: IWorkLocation[] = [{ id: 123 }];
          expectedResult = service.addWorkLocationToCollectionIfMissing(workLocationCollection, ...workLocationArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const workLocation: IWorkLocation = { id: 123 };
          const workLocation2: IWorkLocation = { id: 456 };
          expectedResult = service.addWorkLocationToCollectionIfMissing([], workLocation, workLocation2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(workLocation);
          expect(expectedResult).toContain(workLocation2);
        });

        it('should accept null and undefined values', () => {
          const workLocation: IWorkLocation = { id: 123 };
          expectedResult = service.addWorkLocationToCollectionIfMissing([], null, workLocation, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(workLocation);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
