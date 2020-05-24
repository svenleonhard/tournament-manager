import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHall, Hall } from 'app/shared/model/hall.model';
import { HallService } from './hall.service';

@Component({
  selector: 'jhi-hall-update',
  templateUrl: './hall-update.component.html',
})
export class HallUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hallName: [null, []],
  });

  constructor(protected hallService: HallService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hall }) => {
      this.updateForm(hall);
    });
  }

  updateForm(hall: IHall): void {
    this.editForm.patchValue({
      id: hall.id,
      hallName: hall.hallName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hall = this.createFromForm();
    if (hall.id !== undefined) {
      this.subscribeToSaveResponse(this.hallService.update(hall));
    } else {
      this.subscribeToSaveResponse(this.hallService.create(hall));
    }
  }

  private createFromForm(): IHall {
    return {
      ...new Hall(),
      id: this.editForm.get(['id'])!.value,
      hallName: this.editForm.get(['hallName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHall>>): void {
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
