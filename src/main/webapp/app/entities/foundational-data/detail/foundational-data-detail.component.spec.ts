import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoundationalDataDetailComponent } from './foundational-data-detail.component';

describe('Component Tests', () => {
  describe('FoundationalData Management Detail Component', () => {
    let comp: FoundationalDataDetailComponent;
    let fixture: ComponentFixture<FoundationalDataDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FoundationalDataDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ foundationalData: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FoundationalDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoundationalDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load foundationalData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.foundationalData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
