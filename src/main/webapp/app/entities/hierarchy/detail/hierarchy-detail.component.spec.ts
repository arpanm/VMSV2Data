import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HierarchyDetailComponent } from './hierarchy-detail.component';

describe('Component Tests', () => {
  describe('Hierarchy Management Detail Component', () => {
    let comp: HierarchyDetailComponent;
    let fixture: ComponentFixture<HierarchyDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HierarchyDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ hierarchy: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HierarchyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HierarchyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hierarchy on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hierarchy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
