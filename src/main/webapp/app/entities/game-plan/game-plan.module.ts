import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TournamentManagerSharedModule } from 'app/shared/shared.module';
import { GamePlanComponent } from './game-plan.component';
import { GamePlanDetailComponent } from './game-plan-detail.component';
import { GamePlanUpdateComponent } from './game-plan-update.component';
import { GamePlanDeleteDialogComponent } from './game-plan-delete-dialog.component';
import { gamePlanRoute } from './game-plan.route';

@NgModule({
  imports: [TournamentManagerSharedModule, RouterModule.forChild(gamePlanRoute)],
  declarations: [GamePlanComponent, GamePlanDetailComponent, GamePlanUpdateComponent, GamePlanDeleteDialogComponent],
  entryComponents: [GamePlanDeleteDialogComponent],
})
export class TournamentManagerGamePlanModule {}
