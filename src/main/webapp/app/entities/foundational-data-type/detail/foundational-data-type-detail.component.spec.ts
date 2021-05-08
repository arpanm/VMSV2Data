import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoundationalDataTypeDetailComponent } from './foundational-data-type-detail.component';

describe('Component Tests', () => {
  describe('FoundationalDataType Management Detail Component', () => {
    let comp: FoundationalDataTypeDetailComponent;
    let fixture: ComponentFixture<FoundationalDataTypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FoundationalDataTypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ foundationalDataType: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FoundationalDataTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoundationalDataTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load foundationalDataType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.foundationalDataType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
