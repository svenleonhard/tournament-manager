import { ICommunity } from 'app/shared/model/community.model';

export interface ITeam {
  id?: number;
  teamName?: string;
  playersAmount?: number;
  community?: ICommunity;
}

export class Team implements ITeam {
  constructor(public id?: number, public teamName?: string, public playersAmount?: number, public community?: ICommunity) {}
}
