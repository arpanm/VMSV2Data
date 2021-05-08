jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CustomFieldTypeService } from '../service/custom-field-type.service';

import { CustomFieldTypeDeleteDialogComponent } from './custom-field-type-delete-dialog.component';

describe('Component Tests', () => {
  describe('CustomFieldType Management Delete Component', () => {
    let comp: CustomFieldTypeDeleteDialogComponent;
    let fixture: ComponentFixture<CustomFieldTypeDeleteDialogComponent>;
    let service: CustomFieldTypeService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CustomFieldTypeDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(CustomFieldTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomFieldTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CustomFieldTypeService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
