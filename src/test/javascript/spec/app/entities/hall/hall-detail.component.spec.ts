import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TournamentManagerTestModule } from '../../../test.module';
import { HallDetailComponent } from 'app/entities/hall/hall-detail.component';
import { Hall } from 'app/shared/model/hall.model';

describe('Component Tests', () => {
  describe('Hall Management Detail Component', () => {
    let comp: HallDetailComponent;
    let fixture: ComponentFixture<HallDetailComponent>;
    const route = ({ data: of({ hall: new Hall(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TournamentManagerTestModule],
        declarations: [HallDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HallDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HallDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hall on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hall).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
