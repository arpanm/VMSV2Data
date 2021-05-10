jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IJobCategoryTitle, JobCategoryTitle } from '../job-category-title.model';
import { JobCategoryTitleService } from '../service/job-category-title.service';

import { JobCategoryTitleRoutingResolveService } from './job-category-title-routing-resolve.service';

describe('Service Tests', () => {
  describe('JobCategoryTitle routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: JobCategoryTitleRoutingResolveService;
    let service: JobCategoryTitleService;
    let resultJobCategoryTitle: IJobCategoryTitle | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(JobCategoryTitleRoutingResolveService);
      service = TestBed.inject(JobCategoryTitleService);
      resultJobCategoryTitle = undefined;
    });

    describe('resolve', () => {
      it('should return IJobCategoryTitle returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultJobCategoryTitle = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultJobCategoryTitle).toEqual({ id: 123 });
      });

      it('should return new IJobCategoryTitle if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultJobCategoryTitle = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultJobCategoryTitle).toEqual(new JobCategoryTitle());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultJobCategoryTitle = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultJobCategoryTitle).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
