import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TournamentManagerTestModule } from '../../../test.module';
import { GamePlanComponent } from 'app/entities/game-plan/game-plan.component';
import { GamePlanService } from 'app/entities/game-plan/game-plan.service';
import { GamePlan } from 'app/shared/model/game-plan.model';

describe('Component Tests', () => {
  describe('GamePlan Management Component', () => {
    let comp: GamePlanComponent;
    let fixture: ComponentFixture<GamePlanComponent>;
    let service: GamePlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TournamentManagerTestModule],
        declarations: [GamePlanComponent],
      })
        .overrideTemplate(GamePlanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GamePlanComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GamePlanService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GamePlan(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gamePlans && comp.gamePlans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
