export interface IScore {
  id?: number;
  goalsTeam1?: number;
  goalsTeam2?: number;
}

export class Score implements IScore {
  constructor(public id?: number, public goalsTeam1?: number, public goalsTeam2?: number) {}
}
