jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IWorkLocation, WorkLocation } from '../work-location.model';
import { WorkLocationService } from '../service/work-location.service';

import { WorkLocationRoutingResolveService } from './work-location-routing-resolve.service';

describe('Service Tests', () => {
  describe('WorkLocation routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: WorkLocationRoutingResolveService;
    let service: WorkLocationService;
    let resultWorkLocation: IWorkLocation | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(WorkLocationRoutingResolveService);
      service = TestBed.inject(WorkLocationService);
      resultWorkLocation = undefined;
    });

    describe('resolve', () => {
      it('should return IWorkLocation returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultWorkLocation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultWorkLocation).toEqual({ id: 123 });
      });

      it('should return new IWorkLocation if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultWorkLocation = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultWorkLocation).toEqual(new WorkLocation());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultWorkLocation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultWorkLocation).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
