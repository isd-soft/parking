import {Injectable} from '@angular/core';
import {ParkingLot} from './Model/ParkingLot';
import {Observable, of} from 'rxjs';
import {User} from './Model/User';
import {Statistics} from './Model/Statistics';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  parkingLots: Array<ParkingLot>;
  statistics: Array<Statistics>;

  users: Array<User>;

  private getRandomNum: any;
  private date: Date;

  constructor() {

    this.parkingLots = new Array<ParkingLot>();

    this.getRandomNum = () => {
      return Math.floor((Math.random() * 3) + 1);
    };

    for (let i = 0; i < 10; i++) {
      const parkingLot = new ParkingLot();
      parkingLot.id = i;
      parkingLot.number = i;
      parkingLot.status = this.getRandomNum === 0 ? 'FREE' : this.getRandomNum === 1 ? 'OCCUPIED' : 'UNKNOWN';
      parkingLot.updatedAt = new Date();

      this.parkingLots.push(parkingLot);
    }

    this.users = new Array<User>();

    const user1 = new User('u001', 'Mircea', 'mircea');
    const user2 = new User('u002', 'Alex', 'alex');
    const user3 = new User('u003', 'Pavel', 'pavel');
    const user4 = new User('u004', 'Victor', 'victor');

    this.users.push(user1);
    this.users.push(user2);
    this.users.push(user3);
    this.users.push(user4);


    this.statistics = new Array<Statistics>();

    for (let i = 0; i < 10; i++) {
      this.date = new Date('2020-01-05 9:00');
      this.date.setHours(this.date.getHours() + 10);
      status = this.getRandomNum === 0 ? 'FREE' : this.getRandomNum === 1 ? 'OCCUPIED' : 'UNKNOWN';

      const stats = new Statistics(i + 1, i + 1, status, this.date);
      this.statistics.push(stats);
    }
  }

  getAllParkingLots(): Observable<Array<ParkingLot>> {
    return of(this.parkingLots);
  }

  getAllStats(): Observable<Array<Statistics>> {
    return of(this.statistics);
  }
}
