import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TournamentManagerTestModule } from '../../../test.module';
import { GamePlanDetailComponent } from 'app/entities/game-plan/game-plan-detail.component';
import { GamePlan } from 'app/shared/model/game-plan.model';

describe('Component Tests', () => {
  describe('GamePlan Management Detail Component', () => {
    let comp: GamePlanDetailComponent;
    let fixture: ComponentFixture<GamePlanDetailComponent>;
    const route = ({ data: of({ gamePlan: new GamePlan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TournamentManagerTestModule],
        declarations: [GamePlanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GamePlanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GamePlanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gamePlan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gamePlan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
