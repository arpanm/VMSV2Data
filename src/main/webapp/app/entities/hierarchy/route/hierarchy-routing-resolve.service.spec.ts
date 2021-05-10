jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHierarchy, Hierarchy } from '../hierarchy.model';
import { HierarchyService } from '../service/hierarchy.service';

import { HierarchyRoutingResolveService } from './hierarchy-routing-resolve.service';

describe('Service Tests', () => {
  describe('Hierarchy routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HierarchyRoutingResolveService;
    let service: HierarchyService;
    let resultHierarchy: IHierarchy | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HierarchyRoutingResolveService);
      service = TestBed.inject(HierarchyService);
      resultHierarchy = undefined;
    });

    describe('resolve', () => {
      it('should return IHierarchy returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHierarchy = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHierarchy).toEqual({ id: 123 });
      });

      it('should return new IHierarchy if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHierarchy = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHierarchy).toEqual(new Hierarchy());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHierarchy = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHierarchy).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
