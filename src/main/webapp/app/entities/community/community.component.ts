import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from './community.service';
import { CommunityDeleteDialogComponent } from './community-delete-dialog.component';

@Component({
  selector: 'jhi-community',
  templateUrl: './community.component.html',
})
export class CommunityComponent implements OnInit, OnDestroy {
  communities?: ICommunity[];
  eventSubscriber?: Subscription;

  constructor(protected communityService: CommunityService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.communityService.query().subscribe((res: HttpResponse<ICommunity[]>) => (this.communities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommunities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommunity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommunities(): void {
    this.eventSubscriber = this.eventManager.subscribe('communityListModification', () => this.loadAll());
  }

  delete(community: ICommunity): void {
    const modalRef = this.modalService.open(CommunityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.community = community;
  }
}
