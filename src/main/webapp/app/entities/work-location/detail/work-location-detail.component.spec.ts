import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WorkLocationDetailComponent } from './work-location-detail.component';

describe('Component Tests', () => {
  describe('WorkLocation Management Detail Component', () => {
    let comp: WorkLocationDetailComponent;
    let fixture: ComponentFixture<WorkLocationDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [WorkLocationDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ workLocation: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(WorkLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkLocationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workLocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
