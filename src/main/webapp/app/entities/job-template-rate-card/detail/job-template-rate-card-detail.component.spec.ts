import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobTemplateRateCardDetailComponent } from './job-template-rate-card-detail.component';

describe('Component Tests', () => {
  describe('JobTemplateRateCard Management Detail Component', () => {
    let comp: JobTemplateRateCardDetailComponent;
    let fixture: ComponentFixture<JobTemplateRateCardDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [JobTemplateRateCardDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ jobTemplateRateCard: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(JobTemplateRateCardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobTemplateRateCardDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobTemplateRateCard on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobTemplateRateCard).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
