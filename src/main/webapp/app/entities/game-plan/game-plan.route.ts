import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGamePlan, GamePlan } from 'app/shared/model/game-plan.model';
import { GamePlanService } from './game-plan.service';
import { GamePlanComponent } from './game-plan.component';
import { GamePlanDetailComponent } from './game-plan-detail.component';
import { GamePlanUpdateComponent } from './game-plan-update.component';

@Injectable({ providedIn: 'root' })
export class GamePlanResolve implements Resolve<IGamePlan> {
  constructor(private service: GamePlanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGamePlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gamePlan: HttpResponse<GamePlan>) => {
          if (gamePlan.body) {
            return of(gamePlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GamePlan());
  }
}

export const gamePlanRoute: Routes = [
  {
    path: '',
    component: GamePlanComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.gamePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GamePlanDetailComponent,
    resolve: {
      gamePlan: GamePlanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.gamePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GamePlanUpdateComponent,
    resolve: {
      gamePlan: GamePlanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.gamePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GamePlanUpdateComponent,
    resolve: {
      gamePlan: GamePlanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.gamePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
