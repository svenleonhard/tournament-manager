import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHall, Hall } from 'app/shared/model/hall.model';
import { HallService } from './hall.service';
import { HallComponent } from './hall.component';
import { HallDetailComponent } from './hall-detail.component';
import { HallUpdateComponent } from './hall-update.component';

@Injectable({ providedIn: 'root' })
export class HallResolve implements Resolve<IHall> {
  constructor(private service: HallService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHall> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hall: HttpResponse<Hall>) => {
          if (hall.body) {
            return of(hall.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hall());
  }
}

export const hallRoute: Routes = [
  {
    path: '',
    component: HallComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.hall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HallDetailComponent,
    resolve: {
      hall: HallResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.hall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HallUpdateComponent,
    resolve: {
      hall: HallResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.hall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HallUpdateComponent,
    resolve: {
      hall: HallResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tournamentManagerApp.hall.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
