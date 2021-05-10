import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Region } from 'app/entities/enumerations/region.model';
import { IProgram, Program } from '../program.model';

import { ProgramService } from './program.service';

describe('Service Tests', () => {
  describe('Program Service', () => {
    let service: ProgramService;
    let httpMock: HttpTestingController;
    let elemDefault: IProgram;
    let expectedResult: IProgram | IProgram[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProgramService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        email: 'AAAAAAA',
        phoneNumber: 'AAAAAAA',
        deploymentRegion: Region.Ohio,
        subdomain: 'AAAAAAA',
        implementationManagerEmail: 'AAAAAAA',
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

      it('should create a Program', () => {
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

        service.create(new Program()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Program', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            deploymentRegion: 'BBBBBB',
            subdomain: 'BBBBBB',
            implementationManagerEmail: 'BBBBBB',
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

      it('should partial update a Program', () => {
        const patchObject = Object.assign(
          {
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            deploymentRegion: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_FORMAT),
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          new Program()
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

      it('should return a list of Program', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            deploymentRegion: 'BBBBBB',
            subdomain: 'BBBBBB',
            implementationManagerEmail: 'BBBBBB',
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

      it('should delete a Program', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProgramToCollectionIfMissing', () => {
        it('should add a Program to an empty array', () => {
          const program: IProgram = { id: 123 };
          expectedResult = service.addProgramToCollectionIfMissing([], program);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(program);
        });

        it('should not add a Program to an array that contains it', () => {
          const program: IProgram = { id: 123 };
          const programCollection: IProgram[] = [
            {
              ...program,
            },
            { id: 456 },
          ];
          expectedResult = service.addProgramToCollectionIfMissing(programCollection, program);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Program to an array that doesn't contain it", () => {
          const program: IProgram = { id: 123 };
          const programCollection: IProgram[] = [{ id: 456 }];
          expectedResult = service.addProgramToCollectionIfMissing(programCollection, program);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(program);
        });

        it('should add only unique Program to an array', () => {
          const programArray: IProgram[] = [{ id: 123 }, { id: 456 }, { id: 95994 }];
          const programCollection: IProgram[] = [{ id: 123 }];
          expectedResult = service.addProgramToCollectionIfMissing(programCollection, ...programArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const program: IProgram = { id: 123 };
          const program2: IProgram = { id: 456 };
          expectedResult = service.addProgramToCollectionIfMissing([], program, program2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(program);
          expect(expectedResult).toContain(program2);
        });

        it('should accept null and undefined values', () => {
          const program: IProgram = { id: 123 };
          expectedResult = service.addProgramToCollectionIfMissing([], null, program, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(program);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
