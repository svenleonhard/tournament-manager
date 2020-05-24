import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TournamentManagerTestModule } from '../../../test.module';
import { CommunityComponent } from 'app/entities/community/community.component';
import { CommunityService } from 'app/entities/community/community.service';
import { Community } from 'app/shared/model/community.model';

describe('Component Tests', () => {
  describe('Community Management Component', () => {
    let comp: CommunityComponent;
    let fixture: ComponentFixture<CommunityComponent>;
    let service: CommunityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TournamentManagerTestModule],
        declarations: [CommunityComponent],
      })
        .overrideTemplate(CommunityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommunityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Community(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.communities && comp.communities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
