import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGamePlan } from 'app/shared/model/game-plan.model';
import { GamePlanService } from './game-plan.service';
import { GamePlanDeleteDialogComponent } from './game-plan-delete-dialog.component';

@Component({
  selector: 'jhi-game-plan',
  templateUrl: './game-plan.component.html',
})
export class GamePlanComponent implements OnInit, OnDestroy {
  gamePlans?: IGamePlan[];
  eventSubscriber?: Subscription;

  constructor(protected gamePlanService: GamePlanService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.gamePlanService.query().subscribe((res: HttpResponse<IGamePlan[]>) => (this.gamePlans = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGamePlans();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGamePlan): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGamePlans(): void {
    this.eventSubscriber = this.eventManager.subscribe('gamePlanListModification', () => this.loadAll());
  }

  delete(gamePlan: IGamePlan): void {
    const modalRef = this.modalService.open(GamePlanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gamePlan = gamePlan;
  }
}
