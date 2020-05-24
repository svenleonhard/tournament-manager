import { IGame } from 'app/shared/model/game.model';
import { Mode } from 'app/shared/model/enumerations/mode.model';

export interface IGamePlan {
  id?: number;
  gameMode?: Mode;
  games?: IGame[];
}

export class GamePlan implements IGamePlan {
  constructor(public id?: number, public gameMode?: Mode, public games?: IGame[]) {}
}
