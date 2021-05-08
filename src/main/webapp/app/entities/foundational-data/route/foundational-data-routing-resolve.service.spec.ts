jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFoundationalData, FoundationalData } from '../foundational-data.model';
import { FoundationalDataService } from '../service/foundational-data.service';

import { FoundationalDataRoutingResolveService } from './foundational-data-routing-resolve.service';

describe('Service Tests', () => {
  describe('FoundationalData routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FoundationalDataRoutingResolveService;
    let service: FoundationalDataService;
    let resultFoundationalData: IFoundationalData | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FoundationalDataRoutingResolveService);
      service = TestBed.inject(FoundationalDataService);
      resultFoundationalData = undefined;
    });

    describe('resolve', () => {
      it('should return IFoundationalData returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFoundationalData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFoundationalData).toEqual({ id: 123 });
      });

      it('should return new IFoundationalData if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFoundationalData = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFoundationalData).toEqual(new FoundationalData());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFoundationalData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFoundationalData).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
