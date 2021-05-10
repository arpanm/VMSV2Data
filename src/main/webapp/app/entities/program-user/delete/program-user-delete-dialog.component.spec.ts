jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ProgramUserService } from '../service/program-user.service';

import { ProgramUserDeleteDialogComponent } from './program-user-delete-dialog.component';

describe('Component Tests', () => {
  describe('ProgramUser Management Delete Component', () => {
    let comp: ProgramUserDeleteDialogComponent;
    let fixture: ComponentFixture<ProgramUserDeleteDialogComponent>;
    let service: ProgramUserService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProgramUserDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ProgramUserDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramUserDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ProgramUserService);
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
