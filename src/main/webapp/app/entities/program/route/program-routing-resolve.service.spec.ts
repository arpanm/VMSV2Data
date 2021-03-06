jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProgram, Program } from '../program.model';
import { ProgramService } from '../service/program.service';

import { ProgramRoutingResolveService } from './program-routing-resolve.service';

describe('Service Tests', () => {
  describe('Program routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProgramRoutingResolveService;
    let service: ProgramService;
    let resultProgram: IProgram | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProgramRoutingResolveService);
      service = TestBed.inject(ProgramService);
      resultProgram = undefined;
    });

    describe('resolve', () => {
      it('should return IProgram returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProgram = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProgram).toEqual({ id: 123 });
      });

      it('should return new IProgram if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProgram = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProgram).toEqual(new Program());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProgram = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProgram).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
