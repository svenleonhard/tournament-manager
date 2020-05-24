import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tournament',
        loadChildren: () => import('./tournament/tournament.module').then(m => m.TournamentManagerTournamentModule),
      },
      {
        path: 'community',
        loadChildren: () => import('./community/community.module').then(m => m.TournamentManagerCommunityModule),
      },
      {
        path: 'team',
        loadChildren: () => import('./team/team.module').then(m => m.TournamentManagerTeamModule),
      },
      {
        path: 'hall',
        loadChildren: () => import('./hall/hall.module').then(m => m.TournamentManagerHallModule),
      },
      {
        path: 'game',
        loadChildren: () => import('./game/game.module').then(m => m.TournamentManagerGameModule),
      },
      {
        path: 'score',
        loadChildren: () => import('./score/score.module').then(m => m.TournamentManagerScoreModule),
      },
      {
        path: 'game-plan',
        loadChildren: () => import('./game-plan/game-plan.module').then(m => m.TournamentManagerGamePlanModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TournamentManagerEntityModule {}
