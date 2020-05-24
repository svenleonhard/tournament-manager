import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITournament, Tournament } from 'app/shared/model/tournament.model';
import { TournamentService } from './tournament.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

@Component({
  selector: 'jhi-tournament-update',
  templateUrl: './tournament-update.component.html',
})
export class TournamentUpdateComponent implements OnInit {
  isSaving = false;
  communities: ICommunity[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    tournamentName: [null, []],
    date: [],
    location: [],
    communities: [],
  });

  constructor(
    protected tournamentService: TournamentService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tournament }) => {
      this.updateForm(tournament);

      this.communityService.query().subscribe((res: HttpResponse<ICommunity[]>) => (this.communities = res.body || []));
    });
  }

  updateForm(tournament: ITournament): void {
    this.editForm.patchValue({
      id: tournament.id,
      tournamentName: tournament.tournamentName,
      date: tournament.date,
      location: tournament.location,
      communities: tournament.communities,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tournament = this.createFromForm();
    if (tournament.id !== undefined) {
      this.subscribeToSaveResponse(this.tournamentService.update(tournament));
    } else {
      this.subscribeToSaveResponse(this.tournamentService.create(tournament));
    }
  }

  private createFromForm(): ITournament {
    return {
      ...new Tournament(),
      id: this.editForm.get(['id'])!.value,
      tournamentName: this.editForm.get(['tournamentName'])!.value,
      date: this.editForm.get(['date'])!.value,
      location: this.editForm.get(['location'])!.value,
      communities: this.editForm.get(['communities'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITournament>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICommunity): any {
    return item.id;
  }

  getSelected(selectedVals: ICommunity[], option: ICommunity): ICommunity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
