import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GameService } from 'app/entities/game/game.service';
import { IGame, Game } from 'app/shared/model/game.model';
import { GameState } from 'app/shared/model/enumerations/game-state.model';
import { GameType } from 'app/shared/model/enumerations/game-type.model';

describe('Service Tests', () => {
  describe('Game Service', () => {
    let injector: TestBed;
    let service: GameService;
    let httpMock: HttpTestingController;
    let elemDefault: IGame;
    let expectedResult: IGame | IGame[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GameService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Game(0, 0, GameState.PLANNED, currentDate, 'AAAAAAA', 'AAAAAAA', GameType.GROUP);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Game', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Game()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Game', () => {
        const returnedFromService = Object.assign(
          {
            duration: 1,
            state: 'BBBBBB',
            startTime: currentDate.format(DATE_TIME_FORMAT),
            team1: 'BBBBBB',
            team2: 'BBBBBB',
            gameType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Game', () => {
        const returnedFromService = Object.assign(
          {
            duration: 1,
            state: 'BBBBBB',
            startTime: currentDate.format(DATE_TIME_FORMAT),
            team1: 'BBBBBB',
            team2: 'BBBBBB',
            gameType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Game', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
