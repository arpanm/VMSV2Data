import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Currency } from 'app/entities/enumerations/currency.model';
import { IJobTemplateRateCard, JobTemplateRateCard } from '../job-template-rate-card.model';

import { JobTemplateRateCardService } from './job-template-rate-card.service';

describe('Service Tests', () => {
  describe('JobTemplateRateCard Service', () => {
    let service: JobTemplateRateCardService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobTemplateRateCard;
    let expectedResult: IJobTemplateRateCard | IJobTemplateRateCard[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(JobTemplateRateCardService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        currency: Currency.USD,
        hourlyMin: 0,
        hourlyMax: 0,
        dailyMin: 0,
        dailyMax: 0,
        weeklyMin: 0,
        weeklyMax: 0,
        monthlyMin: 0,
        monthlyMax: 0,
        yearlyMin: 0,
        yearlyMax: 0,
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

      it('should create a JobTemplateRateCard', () => {
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

        service.create(new JobTemplateRateCard()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobTemplateRateCard', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            currency: 'BBBBBB',
            hourlyMin: 1,
            hourlyMax: 1,
            dailyMin: 1,
            dailyMax: 1,
            weeklyMin: 1,
            weeklyMax: 1,
            monthlyMin: 1,
            monthlyMax: 1,
            yearlyMin: 1,
            yearlyMax: 1,
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

      it('should partial update a JobTemplateRateCard', () => {
        const patchObject = Object.assign(
          {
            currency: 'BBBBBB',
            hourlyMax: 1,
            dailyMax: 1,
            weeklyMin: 1,
            weeklyMax: 1,
            monthlyMin: 1,
            yearlyMax: 1,
            createdBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
          },
          new JobTemplateRateCard()
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

      it('should return a list of JobTemplateRateCard', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            currency: 'BBBBBB',
            hourlyMin: 1,
            hourlyMax: 1,
            dailyMin: 1,
            dailyMax: 1,
            weeklyMin: 1,
            weeklyMax: 1,
            monthlyMin: 1,
            monthlyMax: 1,
            yearlyMin: 1,
            yearlyMax: 1,
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

      it('should delete a JobTemplateRateCard', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addJobTemplateRateCardToCollectionIfMissing', () => {
        it('should add a JobTemplateRateCard to an empty array', () => {
          const jobTemplateRateCard: IJobTemplateRateCard = { id: 123 };
          expectedResult = service.addJobTemplateRateCardToCollectionIfMissing([], jobTemplateRateCard);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(jobTemplateRateCard);
        });

        it('should not add a JobTemplateRateCard to an array that contains it', () => {
          const jobTemplateRateCard: IJobTemplateRateCard = { id: 123 };
          const jobTemplateRateCardCollection: IJobTemplateRateCard[] = [
            {
              ...jobTemplateRateCard,
            },
            { id: 456 },
          ];
          expectedResult = service.addJobTemplateRateCardToCollectionIfMissing(jobTemplateRateCardCollection, jobTemplateRateCard);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a JobTemplateRateCard to an array that doesn't contain it", () => {
          const jobTemplateRateCard: IJobTemplateRateCard = { id: 123 };
          const jobTemplateRateCardCollection: IJobTemplateRateCard[] = [{ id: 456 }];
          expectedResult = service.addJobTemplateRateCardToCollectionIfMissing(jobTemplateRateCardCollection, jobTemplateRateCard);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(jobTemplateRateCard);
        });

        it('should add only unique JobTemplateRateCard to an array', () => {
          const jobTemplateRateCardArray: IJobTemplateRateCard[] = [{ id: 123 }, { id: 456 }, { id: 31196 }];
          const jobTemplateRateCardCollection: IJobTemplateRateCard[] = [{ id: 123 }];
          expectedResult = service.addJobTemplateRateCardToCollectionIfMissing(jobTemplateRateCardCollection, ...jobTemplateRateCardArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const jobTemplateRateCard: IJobTemplateRateCard = { id: 123 };
          const jobTemplateRateCard2: IJobTemplateRateCard = { id: 456 };
          expectedResult = service.addJobTemplateRateCardToCollectionIfMissing([], jobTemplateRateCard, jobTemplateRateCard2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(jobTemplateRateCard);
          expect(expectedResult).toContain(jobTemplateRateCard2);
        });

        it('should accept null and undefined values', () => {
          const jobTemplateRateCard: IJobTemplateRateCard = { id: 123 };
          expectedResult = service.addJobTemplateRateCardToCollectionIfMissing([], null, jobTemplateRateCard, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(jobTemplateRateCard);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
