import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGamePlan, GamePlan } from 'app/shared/model/game-plan.model';
import { GamePlanService } from './game-plan.service';

@Component({
  selector: 'jhi-game-plan-update',
  templateUrl: './game-plan-update.component.html',
})
export class GamePlanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    gameMode: [],
  });

  constructor(protected gamePlanService: GamePlanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gamePlan }) => {
      this.updateForm(gamePlan);
    });
  }

  updateForm(gamePlan: IGamePlan): void {
    this.editForm.patchValue({
      id: gamePlan.id,
      gameMode: gamePlan.gameMode,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gamePlan = this.createFromForm();
    if (gamePlan.id !== undefined) {
      this.subscribeToSaveResponse(this.gamePlanService.update(gamePlan));
    } else {
      this.subscribeToSaveResponse(this.gamePlanService.create(gamePlan));
    }
  }

  private createFromForm(): IGamePlan {
    return {
      ...new GamePlan(),
      id: this.editForm.get(['id'])!.value,
      gameMode: this.editForm.get(['gameMode'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGamePlan>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
