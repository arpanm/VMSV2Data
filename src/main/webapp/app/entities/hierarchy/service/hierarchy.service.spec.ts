import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Language } from 'app/entities/enumerations/language.model';
import { Currency } from 'app/entities/enumerations/currency.model';
import { Country } from 'app/entities/enumerations/country.model';
import { IHierarchy, Hierarchy } from '../hierarchy.model';

import { HierarchyService } from './hierarchy.service';

describe('Service Tests', () => {
  describe('Hierarchy Service', () => {
    let service: HierarchyService;
    let httpMock: HttpTestingController;
    let elemDefault: IHierarchy;
    let expectedResult: IHierarchy | IHierarchy[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HierarchyService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        workFlow: 'AAAAAAA',
        preferredLanguage: Language.English,
        preferredCurrency: Currency.USD,
        primaryCountry: Country.US,
        primaryAddress: 'AAAAAAA',
        secondaryCountry: Country.US,
        secondaryAddress: 'AAAAAAA',
        primaryContactName: 'AAAAAAA',
        primaryContactDesignation: 'AAAAAAA',
        primaryContactEmail: 'AAAAAAA',
        primaryContactPhone: 'AAAAAAA',
        secondaryContactName: 'AAAAAAA',
        secondaryContactDesignation: 'AAAAAAA',
        secondaryContactEmail: 'AAAAAAA',
        secondaryContactPhone: 'AAAAAAA',
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

      it('should create a Hierarchy', () => {
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

        service.create(new Hierarchy()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Hierarchy', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            workFlow: 'BBBBBB',
            preferredLanguage: 'BBBBBB',
            preferredCurrency: 'BBBBBB',
            primaryCountry: 'BBBBBB',
            primaryAddress: 'BBBBBB',
            secondaryCountry: 'BBBBBB',
            secondaryAddress: 'BBBBBB',
            primaryContactName: 'BBBBBB',
            primaryContactDesignation: 'BBBBBB',
            primaryContactEmail: 'BBBBBB',
            primaryContactPhone: 'BBBBBB',
            secondaryContactName: 'BBBBBB',
            secondaryContactDesignation: 'BBBBBB',
            secondaryContactEmail: 'BBBBBB',
            secondaryContactPhone: 'BBBBBB',
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

      it('should partial update a Hierarchy', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            workFlow: 'BBBBBB',
            preferredCurrency: 'BBBBBB',
            primaryAddress: 'BBBBBB',
            secondaryContactName: 'BBBBBB',
            secondaryContactDesignation: 'BBBBBB',
            isActive: true,
            createdAt: currentDate.format(DATE_FORMAT),
            updatedBy: 'BBBBBB',
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          new Hierarchy()
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

      it('should return a list of Hierarchy', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            workFlow: 'BBBBBB',
            preferredLanguage: 'BBBBBB',
            preferredCurrency: 'BBBBBB',
            primaryCountry: 'BBBBBB',
            primaryAddress: 'BBBBBB',
            secondaryCountry: 'BBBBBB',
            secondaryAddress: 'BBBBBB',
            primaryContactName: 'BBBBBB',
            primaryContactDesignation: 'BBBBBB',
            primaryContactEmail: 'BBBBBB',
            primaryContactPhone: 'BBBBBB',
            secondaryContactName: 'BBBBBB',
            secondaryContactDesignation: 'BBBBBB',
            secondaryContactEmail: 'BBBBBB',
            secondaryContactPhone: 'BBBBBB',
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

      it('should delete a Hierarchy', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHierarchyToCollectionIfMissing', () => {
        it('should add a Hierarchy to an empty array', () => {
          const hierarchy: IHierarchy = { id: 123 };
          expectedResult = service.addHierarchyToCollectionIfMissing([], hierarchy);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hierarchy);
        });

        it('should not add a Hierarchy to an array that contains it', () => {
          const hierarchy: IHierarchy = { id: 123 };
          const hierarchyCollection: IHierarchy[] = [
            {
              ...hierarchy,
            },
            { id: 456 },
          ];
          expectedResult = service.addHierarchyToCollectionIfMissing(hierarchyCollection, hierarchy);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Hierarchy to an array that doesn't contain it", () => {
          const hierarchy: IHierarchy = { id: 123 };
          const hierarchyCollection: IHierarchy[] = [{ id: 456 }];
          expectedResult = service.addHierarchyToCollectionIfMissing(hierarchyCollection, hierarchy);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hierarchy);
        });

        it('should add only unique Hierarchy to an array', () => {
          const hierarchyArray: IHierarchy[] = [{ id: 123 }, { id: 456 }, { id: 22899 }];
          const hierarchyCollection: IHierarchy[] = [{ id: 123 }];
          expectedResult = service.addHierarchyToCollectionIfMissing(hierarchyCollection, ...hierarchyArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const hierarchy: IHierarchy = { id: 123 };
          const hierarchy2: IHierarchy = { id: 456 };
          expectedResult = service.addHierarchyToCollectionIfMissing([], hierarchy, hierarchy2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hierarchy);
          expect(expectedResult).toContain(hierarchy2);
        });

        it('should accept null and undefined values', () => {
          const hierarchy: IHierarchy = { id: 123 };
          expectedResult = service.addHierarchyToCollectionIfMissing([], null, hierarchy, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hierarchy);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
