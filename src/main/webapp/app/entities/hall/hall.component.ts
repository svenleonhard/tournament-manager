import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHall } from 'app/shared/model/hall.model';
import { HallService } from './hall.service';
import { HallDeleteDialogComponent } from './hall-delete-dialog.component';

@Component({
  selector: 'jhi-hall',
  templateUrl: './hall.component.html',
})
export class HallComponent implements OnInit, OnDestroy {
  halls?: IHall[];
  eventSubscriber?: Subscription;

  constructor(protected hallService: HallService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.hallService.query().subscribe((res: HttpResponse<IHall[]>) => (this.halls = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHalls();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHall): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHalls(): void {
    this.eventSubscriber = this.eventManager.subscribe('hallListModification', () => this.loadAll());
  }

  delete(hall: IHall): void {
    const modalRef = this.modalService.open(HallDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hall = hall;
  }
}
