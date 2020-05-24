import { Moment } from 'moment';
import { ICommunity } from 'app/shared/model/community.model';

export interface ITournament {
  id?: number;
  tournamentName?: string;
  date?: Moment;
  location?: string;
  communities?: ICommunity[];
}

export class Tournament implements ITournament {
  constructor(
    public id?: number,
    public tournamentName?: string,
    public date?: Moment,
    public location?: string,
    public communities?: ICommunity[]
  ) {}
}
