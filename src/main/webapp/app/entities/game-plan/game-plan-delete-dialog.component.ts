import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGamePlan } from 'app/shared/model/game-plan.model';
import { GamePlanService } from './game-plan.service';

@Component({
  templateUrl: './game-plan-delete-dialog.component.html',
})
export class GamePlanDeleteDialogComponent {
  gamePlan?: IGamePlan;

  constructor(protected gamePlanService: GamePlanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gamePlanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gamePlanListModification');
      this.activeModal.close();
    });
  }
}
