import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomFieldDataDetailComponent } from './custom-field-data-detail.component';

describe('Component Tests', () => {
  describe('CustomFieldData Management Detail Component', () => {
    let comp: CustomFieldDataDetailComponent;
    let fixture: ComponentFixture<CustomFieldDataDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CustomFieldDataDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ customFieldData: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CustomFieldDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomFieldDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customFieldData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customFieldData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
