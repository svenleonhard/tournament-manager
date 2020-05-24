import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITournament } from 'app/shared/model/tournament.model';
import { TournamentService } from './tournament.service';
import { TournamentDeleteDialogComponent } from './tournament-delete-dialog.component';

@Component({
  selector: 'jhi-tournament',
  templateUrl: './tournament.component.html',
})
export class TournamentComponent implements OnInit, OnDestroy {
  tournaments?: ITournament[];
  eventSubscriber?: Subscription;

  constructor(protected tournamentService: TournamentService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tournamentService.query().subscribe((res: HttpResponse<ITournament[]>) => (this.tournaments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTournaments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITournament): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTournaments(): void {
    this.eventSubscriber = this.eventManager.subscribe('tournamentListModification', () => this.loadAll());
  }

  delete(tournament: ITournament): void {
    const modalRef = this.modalService.open(TournamentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tournament = tournament;
  }
}
