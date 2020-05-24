import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScore, Score } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';

@Component({
  selector: 'jhi-score-update',
  templateUrl: './score-update.component.html',
})
export class ScoreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    goalsTeam1: [],
    goalsTeam2: [],
  });

  constructor(protected scoreService: ScoreService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ score }) => {
      this.updateForm(score);
    });
  }

  updateForm(score: IScore): void {
    this.editForm.patchValue({
      id: score.id,
      goalsTeam1: score.goalsTeam1,
      goalsTeam2: score.goalsTeam2,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const score = this.createFromForm();
    if (score.id !== undefined) {
      this.subscribeToSaveResponse(this.scoreService.update(score));
    } else {
      this.subscribeToSaveResponse(this.scoreService.create(score));
    }
  }

  private createFromForm(): IScore {
    return {
      ...new Score(),
      id: this.editForm.get(['id'])!.value,
      goalsTeam1: this.editForm.get(['goalsTeam1'])!.value,
      goalsTeam2: this.editForm.get(['goalsTeam2'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScore>>): void {
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
}
