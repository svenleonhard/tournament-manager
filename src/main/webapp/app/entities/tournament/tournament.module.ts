import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TournamentManagerSharedModule } from 'app/shared/shared.module';
import { TournamentComponent } from './tournament.component';
import { TournamentDetailComponent } from './tournament-detail.component';
import { TournamentUpdateComponent } from './tournament-update.component';
import { TournamentDeleteDialogComponent } from './tournament-delete-dialog.component';
import { tournamentRoute } from './tournament.route';

@NgModule({
  imports: [TournamentManagerSharedModule, RouterModule.forChild(tournamentRoute)],
  declarations: [TournamentComponent, TournamentDetailComponent, TournamentUpdateComponent, TournamentDeleteDialogComponent],
  entryComponents: [TournamentDeleteDialogComponent],
})
export class TournamentManagerTournamentModule {}
