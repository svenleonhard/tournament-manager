import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGamePlan } from 'app/shared/model/game-plan.model';

@Component({
  selector: 'jhi-game-plan-detail',
  templateUrl: './game-plan-detail.component.html',
})
export class GamePlanDetailComponent implements OnInit {
  gamePlan: IGamePlan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gamePlan }) => (this.gamePlan = gamePlan));
  }

  previousState(): void {
    window.history.back();
  }
}
