import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobCategoryTitleDetailComponent } from './job-category-title-detail.component';

describe('Component Tests', () => {
  describe('JobCategoryTitle Management Detail Component', () => {
    let comp: JobCategoryTitleDetailComponent;
    let fixture: ComponentFixture<JobCategoryTitleDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [JobCategoryTitleDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ jobCategoryTitle: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(JobCategoryTitleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobCategoryTitleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobCategoryTitle on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobCategoryTitle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
