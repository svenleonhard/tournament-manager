import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITournament, Tournament } from 'app/shared/model/tournament.model';
import { TournamentService } from './tournament.service';
import { TournamentComponent } from './tournament.component';
import { TournamentDetailComponent } from './tournament-detail.component';
import { TournamentUpdateComponent } from './tournament-update.component';

@Injectable({ providedIn: 'root' })
export class TournamentResolve implements Resolve<ITournament> {
  constructor(private service: TournamentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITournament> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tournament: HttpResponse<Tournament>) => {
          if (tournament.body) {
            return of(tournament.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tournament());
  }
}

export const tournamentRoute: Routes = [
  {
    path: '',
    component: TournamentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.tournament.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TournamentDetailComponent,
    resolve: {
      tournament: TournamentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.tournament.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TournamentUpdateComponent,
    resolve: {
      tournament: TournamentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.tournament.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TournamentUpdateComponent,
    resolve: {
      tournament: TournamentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.tournament.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
