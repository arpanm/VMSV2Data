import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IJobCategoryTitle, JobCategoryTitle } from '../job-category-title.model';

import { JobCategoryTitleService } from './job-category-title.service';

describe('Service Tests', () => {
  describe('JobCategoryTitle Service', () => {
    let service: JobCategoryTitleService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobCategoryTitle;
    let expectedResult: IJobCategoryTitle | IJobCategoryTitle[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(JobCategoryTitleService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        category: 'AAAAAAA',
        title: 'AAAAAAA',
        code: 'AAAAAAA',
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

      it('should create a JobCategoryTitle', () => {
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

        service.create(new JobCategoryTitle()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobCategoryTitle', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            category: 'BBBBBB',
            title: 'BBBBBB',
            code: 'BBBBBB',
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

      it('should partial update a JobCategoryTitle', () => {
        const patchObject = Object.assign(
          {
            isActive: true,
            createdBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          new JobCategoryTitle()
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

      it('should return a list of JobCategoryTitle', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            category: 'BBBBBB',
            title: 'BBBBBB',
            code: 'BBBBBB',
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

      it('should delete a JobCategoryTitle', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addJobCategoryTitleToCollectionIfMissing', () => {
        it('should add a JobCategoryTitle to an empty array', () => {
          const jobCategoryTitle: IJobCategoryTitle = { id: 123 };
          expectedResult = service.addJobCategoryTitleToCollectionIfMissing([], jobCategoryTitle);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(jobCategoryTitle);
        });

        it('should not add a JobCategoryTitle to an array that contains it', () => {
          const jobCategoryTitle: IJobCategoryTitle = { id: 123 };
          const jobCategoryTitleCollection: IJobCategoryTitle[] = [
            {
              ...jobCategoryTitle,
            },
            { id: 456 },
          ];
          expectedResult = service.addJobCategoryTitleToCollectionIfMissing(jobCategoryTitleCollection, jobCategoryTitle);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a JobCategoryTitle to an array that doesn't contain it", () => {
          const jobCategoryTitle: IJobCategoryTitle = { id: 123 };
          const jobCategoryTitleCollection: IJobCategoryTitle[] = [{ id: 456 }];
          expectedResult = service.addJobCategoryTitleToCollectionIfMissing(jobCategoryTitleCollection, jobCategoryTitle);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(jobCategoryTitle);
        });

        it('should add only unique JobCategoryTitle to an array', () => {
          const jobCategoryTitleArray: IJobCategoryTitle[] = [{ id: 123 }, { id: 456 }, { id: 8263 }];
          const jobCategoryTitleCollection: IJobCategoryTitle[] = [{ id: 123 }];
          expectedResult = service.addJobCategoryTitleToCollectionIfMissing(jobCategoryTitleCollection, ...jobCategoryTitleArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const jobCategoryTitle: IJobCategoryTitle = { id: 123 };
          const jobCategoryTitle2: IJobCategoryTitle = { id: 456 };
          expectedResult = service.addJobCategoryTitleToCollectionIfMissing([], jobCategoryTitle, jobCategoryTitle2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(jobCategoryTitle);
          expect(expectedResult).toContain(jobCategoryTitle2);
        });

        it('should accept null and undefined values', () => {
          const jobCategoryTitle: IJobCategoryTitle = { id: 123 };
          expectedResult = service.addJobCategoryTitleToCollectionIfMissing([], null, jobCategoryTitle, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(jobCategoryTitle);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
