import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TournamentManagerTestModule } from '../../../test.module';
import { HallComponent } from 'app/entities/hall/hall.component';
import { HallService } from 'app/entities/hall/hall.service';
import { Hall } from 'app/shared/model/hall.model';

describe('Component Tests', () => {
  describe('Hall Management Component', () => {
    let comp: HallComponent;
    let fixture: ComponentFixture<HallComponent>;
    let service: HallService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TournamentManagerTestModule],
        declarations: [HallComponent],
      })
        .overrideTemplate(HallComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HallComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HallService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Hall(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.halls && comp.halls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
