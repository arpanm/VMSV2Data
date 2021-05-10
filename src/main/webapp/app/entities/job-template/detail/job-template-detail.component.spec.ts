import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobTemplateDetailComponent } from './job-template-detail.component';

describe('Component Tests', () => {
  describe('JobTemplate Management Detail Component', () => {
    let comp: JobTemplateDetailComponent;
    let fixture: ComponentFixture<JobTemplateDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [JobTemplateDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ jobTemplate: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(JobTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobTemplate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
