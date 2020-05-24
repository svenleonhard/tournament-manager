import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TournamentManagerTestModule } from '../../../test.module';
import { GamePlanUpdateComponent } from 'app/entities/game-plan/game-plan-update.component';
import { GamePlanService } from 'app/entities/game-plan/game-plan.service';
import { GamePlan } from 'app/shared/model/game-plan.model';

describe('Component Tests', () => {
  describe('GamePlan Management Update Component', () => {
    let comp: GamePlanUpdateComponent;
    let fixture: ComponentFixture<GamePlanUpdateComponent>;
    let service: GamePlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TournamentManagerTestModule],
        declarations: [GamePlanUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GamePlanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GamePlanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GamePlanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GamePlan(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GamePlan();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
