import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgramUserDetailComponent } from './program-user-detail.component';

describe('Component Tests', () => {
  describe('ProgramUser Management Detail Component', () => {
    let comp: ProgramUserDetailComponent;
    let fixture: ComponentFixture<ProgramUserDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ProgramUserDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ programUser: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ProgramUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load programUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.programUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
