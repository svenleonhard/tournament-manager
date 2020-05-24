import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITournament } from 'app/shared/model/tournament.model';

type EntityResponseType = HttpResponse<ITournament>;
type EntityArrayResponseType = HttpResponse<ITournament[]>;

@Injectable({ providedIn: 'root' })
export class TournamentService {
  public resourceUrl = SERVER_API_URL + 'api/tournaments';

  constructor(protected http: HttpClient) {}

  create(tournament: ITournament): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tournament);
    return this.http
      .post<ITournament>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tournament: ITournament): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tournament);
    return this.http
      .put<ITournament>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITournament>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITournament[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tournament: ITournament): ITournament {
    const copy: ITournament = Object.assign({}, tournament, {
      date: tournament.date && tournament.date.isValid() ? tournament.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tournament: ITournament) => {
        tournament.date = tournament.date ? moment(tournament.date) : undefined;
      });
    }
    return res;
  }
}
