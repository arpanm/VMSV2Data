import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Role } from 'app/entities/enumerations/role.model';
import { IProgramUser, ProgramUser } from '../program-user.model';

import { ProgramUserService } from './program-user.service';

describe('Service Tests', () => {
  describe('ProgramUser Service', () => {
    let service: ProgramUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IProgramUser;
    let expectedResult: IProgramUser | IProgramUser[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProgramUserService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        firstName: 'AAAAAAA',
        lastName: 'AAAAAAA',
        email: 'AAAAAAA',
        sourceId: 'AAAAAAA',
        phoneNumber: 'AAAAAAA',
        clientDesignation: 'AAAAAAA',
        simplifyRole: Role.Administrator,
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

      it('should create a ProgramUser', () => {
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

        service.create(new ProgramUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProgramUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            sourceId: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            clientDesignation: 'BBBBBB',
            simplifyRole: 'BBBBBB',
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

      it('should partial update a ProgramUser', () => {
        const patchObject = Object.assign(
          {
            firstName: 'BBBBBB',
            sourceId: 'BBBBBB',
            clientDesignation: 'BBBBBB',
            fileUploadPath: 'BBBBBB',
            isActive: true,
            updatedAt: currentDate.format(DATE_FORMAT),
          },
          new ProgramUser()
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

      it('should return a list of ProgramUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            sourceId: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            clientDesignation: 'BBBBBB',
            simplifyRole: 'BBBBBB',
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

      it('should delete a ProgramUser', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProgramUserToCollectionIfMissing', () => {
        it('should add a ProgramUser to an empty array', () => {
          const programUser: IProgramUser = { id: 123 };
          expectedResult = service.addProgramUserToCollectionIfMissing([], programUser);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(programUser);
        });

        it('should not add a ProgramUser to an array that contains it', () => {
          const programUser: IProgramUser = { id: 123 };
          const programUserCollection: IProgramUser[] = [
            {
              ...programUser,
            },
            { id: 456 },
          ];
          expectedResult = service.addProgramUserToCollectionIfMissing(programUserCollection, programUser);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ProgramUser to an array that doesn't contain it", () => {
          const programUser: IProgramUser = { id: 123 };
          const programUserCollection: IProgramUser[] = [{ id: 456 }];
          expectedResult = service.addProgramUserToCollectionIfMissing(programUserCollection, programUser);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(programUser);
        });

        it('should add only unique ProgramUser to an array', () => {
          const programUserArray: IProgramUser[] = [{ id: 123 }, { id: 456 }, { id: 15413 }];
          const programUserCollection: IProgramUser[] = [{ id: 123 }];
          expectedResult = service.addProgramUserToCollectionIfMissing(programUserCollection, ...programUserArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const programUser: IProgramUser = { id: 123 };
          const programUser2: IProgramUser = { id: 456 };
          expectedResult = service.addProgramUserToCollectionIfMissing([], programUser, programUser2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(programUser);
          expect(expectedResult).toContain(programUser2);
        });

        it('should accept null and undefined values', () => {
          const programUser: IProgramUser = { id: 123 };
          expectedResult = service.addProgramUserToCollectionIfMissing([], null, programUser, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(programUser);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
