import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TournamentManagerSharedModule } from 'app/shared/shared.module';
import { HallComponent } from './hall.component';
import { HallDetailComponent } from './hall-detail.component';
import { HallUpdateComponent } from './hall-update.component';
import { HallDeleteDialogComponent } from './hall-delete-dialog.component';
import { hallRoute } from './hall.route';

@NgModule({
  imports: [TournamentManagerSharedModule, RouterModule.forChild(hallRoute)],
  declarations: [HallComponent, HallDetailComponent, HallUpdateComponent, HallDeleteDialogComponent],
  entryComponents: [HallDeleteDialogComponent],
})
export class TournamentManagerHallModule {}
