import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGame, Game } from 'app/shared/model/game.model';
import { GameService } from './game.service';
import { IHall } from 'app/shared/model/hall.model';
import { HallService } from 'app/entities/hall/hall.service';
import { IScore } from 'app/shared/model/score.model';
import { ScoreService } from 'app/entities/score/score.service';
import { IGamePlan } from 'app/shared/model/game-plan.model';
import { GamePlanService } from 'app/entities/game-plan/game-plan.service';

type SelectableEntity = IHall | IScore | IGamePlan;

@Component({
  selector: 'jhi-game-update',
  templateUrl: './game-update.component.html',
})
export class GameUpdateComponent implements OnInit {
  isSaving = false;
  halls: IHall[] = [];
  scores: IScore[] = [];
  gameplans: IGamePlan[] = [];

  editForm = this.fb.group({
    id: [],
    duration: [],
    state: [],
    startTime: [],
    team1: [],
    team2: [],
    gameType: [],
    hall: [],
    score: [],
    gamePlan: [],
  });

  constructor(
    protected gameService: GameService,
    protected hallService: HallService,
    protected scoreService: ScoreService,
    protected gamePlanService: GamePlanService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ game }) => {
      if (!game.id) {
        const today = moment().startOf('day');
        game.startTime = today;
      }

      this.updateForm(game);

      this.hallService
        .query({ filter: 'game-is-null' })
        .pipe(
          map((res: HttpResponse<IHall[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IHall[]) => {
          if (!game.hall || !game.hall.id) {
            this.halls = resBody;
          } else {
            this.hallService
              .find(game.hall.id)
              .pipe(
                map((subRes: HttpResponse<IHall>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IHall[]) => (this.halls = concatRes));
          }
        });

      this.scoreService
        .query({ filter: 'game-is-null' })
        .pipe(
          map((res: HttpResponse<IScore[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IScore[]) => {
          if (!game.score || !game.score.id) {
            this.scores = resBody;
          } else {
            this.scoreService
              .find(game.score.id)
              .pipe(
                map((subRes: HttpResponse<IScore>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IScore[]) => (this.scores = concatRes));
          }
        });

      this.gamePlanService.query().subscribe((res: HttpResponse<IGamePlan[]>) => (this.gameplans = res.body || []));
    });
  }

  updateForm(game: IGame): void {
    this.editForm.patchValue({
      id: game.id,
      duration: game.duration,
      state: game.state,
      startTime: game.startTime ? game.startTime.format(DATE_TIME_FORMAT) : null,
      team1: game.team1,
      team2: game.team2,
      gameType: game.gameType,
      hall: game.hall,
      score: game.score,
      gamePlan: game.gamePlan,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const game = this.createFromForm();
    if (game.id !== undefined) {
      this.subscribeToSaveResponse(this.gameService.update(game));
    } else {
      this.subscribeToSaveResponse(this.gameService.create(game));
    }
  }

  private createFromForm(): IGame {
    return {
      ...new Game(),
      id: this.editForm.get(['id'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      state: this.editForm.get(['state'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      team1: this.editForm.get(['team1'])!.value,
      team2: this.editForm.get(['team2'])!.value,
      gameType: this.editForm.get(['gameType'])!.value,
      hall: this.editForm.get(['hall'])!.value,
      score: this.editForm.get(['score'])!.value,
      gamePlan: this.editForm.get(['gamePlan'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
