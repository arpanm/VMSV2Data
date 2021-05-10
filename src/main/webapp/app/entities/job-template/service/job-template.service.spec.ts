import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { JobTemplateDistributionType } from 'app/entities/enumerations/job-template-distribution-type.model';
import { IJobTemplate, JobTemplate } from '../job-template.model';

import { JobTemplateService } from './job-template.service';

describe('Service Tests', () => {
  describe('JobTemplate Service', () => {
    let service: JobTemplateService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobTemplate;
    let expectedResult: IJobTemplate | IJobTemplate[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(JobTemplateService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        templateTitle: 'AAAAAAA',
        description: 'AAAAAAA',
        jobLevel: 0,
        isDescriptionEditable: false,
        distributionType: JobTemplateDistributionType.Automatic_Submit,
        distributionLimit: 0,
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

      it('should create a JobTemplate', () => {
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

        service.create(new JobTemplate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobTemplate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            templateTitle: 'BBBBBB',
            description: 'BBBBBB',
            jobLevel: 1,
            isDescriptionEditable: true,
            distributionType: 'BBBBBB',
            distributionLimit: 1,
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

      it('should partial update a JobTemplate', () => {
        const patchObject = Object.assign(
          {
            templateTitle: 'BBBBBB',
            description: 'BBBBBB',
            jobLevel: 1,
            isDescriptionEditable: true,
            distributionType: 'BBBBBB',
          },
          new JobTemplate()
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

      it('should return a list of JobTemplate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            templateTitle: 'BBBBBB',
            description: 'BBBBBB',
            jobLevel: 1,
            isDescriptionEditable: true,
            distributionType: 'BBBBBB',
            distributionLimit: 1,
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

      it('should delete a JobTemplate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addJobTemplateToCollectionIfMissing', () => {
        it('should add a JobTemplate to an empty array', () => {
          const jobTemplate: IJobTemplate = { id: 123 };
          expectedResult = service.addJobTemplateToCollectionIfMissing([], jobTemplate);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(jobTemplate);
        });

        it('should not add a JobTemplate to an array that contains it', () => {
          const jobTemplate: IJobTemplate = { id: 123 };
          const jobTemplateCollection: IJobTemplate[] = [
            {
              ...jobTemplate,
            },
            { id: 456 },
          ];
          expectedResult = service.addJobTemplateToCollectionIfMissing(jobTemplateCollection, jobTemplate);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a JobTemplate to an array that doesn't contain it", () => {
          const jobTemplate: IJobTemplate = { id: 123 };
          const jobTemplateCollection: IJobTemplate[] = [{ id: 456 }];
          expectedResult = service.addJobTemplateToCollectionIfMissing(jobTemplateCollection, jobTemplate);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(jobTemplate);
        });

        it('should add only unique JobTemplate to an array', () => {
          const jobTemplateArray: IJobTemplate[] = [{ id: 123 }, { id: 456 }, { id: 7962 }];
          const jobTemplateCollection: IJobTemplate[] = [{ id: 123 }];
          expectedResult = service.addJobTemplateToCollectionIfMissing(jobTemplateCollection, ...jobTemplateArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const jobTemplate: IJobTemplate = { id: 123 };
          const jobTemplate2: IJobTemplate = { id: 456 };
          expectedResult = service.addJobTemplateToCollectionIfMissing([], jobTemplate, jobTemplate2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(jobTemplate);
          expect(expectedResult).toContain(jobTemplate2);
        });

        it('should accept null and undefined values', () => {
          const jobTemplate: IJobTemplate = { id: 123 };
          expectedResult = service.addJobTemplateToCollectionIfMissing([], null, jobTemplate, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(jobTemplate);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
