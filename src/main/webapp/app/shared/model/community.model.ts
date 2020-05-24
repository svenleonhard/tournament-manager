import { ITeam } from 'app/shared/model/team.model';
import { ITournament } from 'app/shared/model/tournament.model';

export interface ICommunity {
  id?: number;
  name?: string;
  teams?: ITeam[];
  tournaments?: ITournament[];
}

export class Community implements ICommunity {
  constructor(public id?: number, public name?: string, public teams?: ITeam[], public tournaments?: ITournament[]) {}
}
