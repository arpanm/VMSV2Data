jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProgramUser, ProgramUser } from '../program-user.model';
import { ProgramUserService } from '../service/program-user.service';

import { ProgramUserRoutingResolveService } from './program-user-routing-resolve.service';

describe('Service Tests', () => {
  describe('ProgramUser routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProgramUserRoutingResolveService;
    let service: ProgramUserService;
    let resultProgramUser: IProgramUser | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProgramUserRoutingResolveService);
      service = TestBed.inject(ProgramUserService);
      resultProgramUser = undefined;
    });

    describe('resolve', () => {
      it('should return IProgramUser returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProgramUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProgramUser).toEqual({ id: 123 });
      });

      it('should return new IProgramUser if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProgramUser = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProgramUser).toEqual(new ProgramUser());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProgramUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProgramUser).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
