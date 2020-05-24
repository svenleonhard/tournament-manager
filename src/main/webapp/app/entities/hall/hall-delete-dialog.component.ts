import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHall } from 'app/shared/model/hall.model';
import { HallService } from './hall.service';

@Component({
  templateUrl: './hall-delete-dialog.component.html',
})
export class HallDeleteDialogComponent {
  hall?: IHall;

  constructor(protected hallService: HallService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hallService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hallListModification');
      this.activeModal.close();
    });
  }
}
