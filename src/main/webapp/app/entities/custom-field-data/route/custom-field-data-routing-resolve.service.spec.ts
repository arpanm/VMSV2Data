jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICustomFieldData, CustomFieldData } from '../custom-field-data.model';
import { CustomFieldDataService } from '../service/custom-field-data.service';

import { CustomFieldDataRoutingResolveService } from './custom-field-data-routing-resolve.service';

describe('Service Tests', () => {
  describe('CustomFieldData routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CustomFieldDataRoutingResolveService;
    let service: CustomFieldDataService;
    let resultCustomFieldData: ICustomFieldData | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CustomFieldDataRoutingResolveService);
      service = TestBed.inject(CustomFieldDataService);
      resultCustomFieldData = undefined;
    });

    describe('resolve', () => {
      it('should return ICustomFieldData returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomFieldData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomFieldData).toEqual({ id: 123 });
      });

      it('should return new ICustomFieldData if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomFieldData = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCustomFieldData).toEqual(new CustomFieldData());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCustomFieldData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCustomFieldData).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
