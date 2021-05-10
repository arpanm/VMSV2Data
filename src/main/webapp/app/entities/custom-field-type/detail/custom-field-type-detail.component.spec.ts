import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomFieldTypeDetailComponent } from './custom-field-type-detail.component';

describe('Component Tests', () => {
  describe('CustomFieldType Management Detail Component', () => {
    let comp: CustomFieldTypeDetailComponent;
    let fixture: ComponentFixture<CustomFieldTypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CustomFieldTypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ customFieldType: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CustomFieldTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomFieldTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customFieldType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customFieldType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
