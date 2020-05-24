export interface IHall {
  id?: number;
  hallName?: string;
}

export class Hall implements IHall {
  constructor(public id?: number, public hallName?: string) {}
}
