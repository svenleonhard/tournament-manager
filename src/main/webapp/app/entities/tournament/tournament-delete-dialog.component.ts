import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITournament } from 'app/shared/model/tournament.model';
import { TournamentService } from './tournament.service';

@Component({
  templateUrl: './tournament-delete-dialog.component.html',
})
export class TournamentDeleteDialogComponent {
  tournament?: ITournament;

  constructor(
    protected tournamentService: TournamentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tournamentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tournamentListModification');
      this.activeModal.close();
    });
  }
}
