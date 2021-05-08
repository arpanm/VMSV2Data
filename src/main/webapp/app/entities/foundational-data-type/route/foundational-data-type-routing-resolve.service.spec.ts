jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFoundationalDataType, FoundationalDataType } from '../foundational-data-type.model';
import { FoundationalDataTypeService } from '../service/foundational-data-type.service';

import { FoundationalDataTypeRoutingResolveService } from './foundational-data-type-routing-resolve.service';

describe('Service Tests', () => {
  describe('FoundationalDataType routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FoundationalDataTypeRoutingResolveService;
    let service: FoundationalDataTypeService;
    let resultFoundationalDataType: IFoundationalDataType | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FoundationalDataTypeRoutingResolveService);
      service = TestBed.inject(FoundationalDataTypeService);
      resultFoundationalDataType = undefined;
    });

    describe('resolve', () => {
      it('should return IFoundationalDataType returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFoundationalDataType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFoundationalDataType).toEqual({ id: 123 });
      });

      it('should return new IFoundationalDataType if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFoundationalDataType = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFoundationalDataType).toEqual(new FoundationalDataType());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFoundationalDataType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFoundationalDataType).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
