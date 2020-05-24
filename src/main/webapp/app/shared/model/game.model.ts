import { Moment } from 'moment';
import { IHall } from 'app/shared/model/hall.model';
import { IScore } from 'app/shared/model/score.model';
import { IGamePlan } from 'app/shared/model/game-plan.model';
import { GameState } from 'app/shared/model/enumerations/game-state.model';
import { GameType } from 'app/shared/model/enumerations/game-type.model';

export interface IGame {
  id?: number;
  duration?: number;
  state?: GameState;
  startTime?: Moment;
  team1?: string;
  team2?: string;
  gameType?: GameType;
  hall?: IHall;
  score?: IScore;
  gamePlan?: IGamePlan;
}

export class Game implements IGame {
  constructor(
    public id?: number,
    public duration?: number,
    public state?: GameState,
    public startTime?: Moment,
    public team1?: string,
    public team2?: string,
    public gameType?: GameType,
    public hall?: IHall,
    public score?: IScore,
    public gamePlan?: IGamePlan
  ) {}
}
