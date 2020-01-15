import { Injectable } from '@angular/core';
import { ParkingLot } from './Model/ParkingLot';
import { Observable, of } from 'rxjs';
import { User } from './Model/User';
import { Statistics } from './Model/Statistics';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  parkingLots: Array<ParkingLot>;
  statistics: Array<Statistics>;

  users: Array<User>;

  constructor() {

    this.parkingLots = new Array<ParkingLot>();

    const pl1 = new ParkingLot();
    pl1.id = 1;
    pl1.number = 1;
    pl1.status = 'FREE';
    pl1.updatedAt = new Date();

    this.parkingLots.push(pl1);

    const pl2 = new ParkingLot();
    pl2.id = 2;
    pl2.number = 2;
    pl2.status = 'FREE';
    pl2.updatedAt = new Date();

    this.parkingLots.push(pl2);

    const pl3 = new ParkingLot();
    pl3.id = 3;
    pl3.number = 3;
    pl3.status = 'FREE';
    pl3.updatedAt = new Date();

    this.parkingLots.push(pl3);

    const pl4 = new ParkingLot();
    pl4.id = 4;
    pl4.number = 4;
    pl4.status = 'FREE';
    pl4.updatedAt = new Date();

    this.parkingLots.push(pl4);

    const pl5 = new ParkingLot();
    pl5.id = 5;
    pl5.number = 5;
    pl5.status = 'OCCUPIED';
    pl5.updatedAt = new Date();

    this.parkingLots.push(pl5);

    const pl6 = new ParkingLot();
    pl6.id = 6;
    pl6.number = 6;
    pl6.status = 'FREE';
    pl6.updatedAt = new Date();

    this.parkingLots.push(pl6);

    const pl7 = new ParkingLot();
    pl7.id = 7;
    pl7.number = 7;
    pl7.status = 'FREE';
    pl7.updatedAt = new Date();

    this.parkingLots.push(pl7);

    const pl8 = new ParkingLot();
    pl8.id = 8;
    pl8.number = 8;
    pl8.status = 'OCCUPIED';
    pl8.updatedAt = new Date();

    this.parkingLots.push(pl8);

    const pl9 = new ParkingLot();
    pl9.id = 9;
    pl9.number = 9;
    pl9.status = 'FREE';
    pl9.updatedAt = new Date();

    this.parkingLots.push(pl9);

    const pl10 = new ParkingLot();
    pl10.id = 10;
    pl10.number = 10;
    pl10.status = 'FREE';
    pl10.updatedAt = new Date();

    this.parkingLots.push(pl10);


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

    const stats1 = new Statistics(1, 1, 'UNKNOWN', new Date('01-05-2020 07:00'));
    const stats2 = new Statistics(2, 7, 'OCCUPIED', new Date('01-08-2020 08:00:00'));
    const stats3 = new Statistics(3, 10, 'OCCUPIED', new Date('2020-01-08 08:04'));
    const stats4 = new Statistics(4, 1, 'OCCUPIED', new Date('2020-01-05 08:05'));
    const stats5 = new Statistics(5, 3, 'OCCUPIED', new Date('2020-01-08 08:10'));
    const stats6 = new Statistics(6, 5, 'FREE', new Date('2020-01-05 12:00'));
    const stats7 = new Statistics(7, 7, 'FREE', new Date('2020-01-08 13:00'));

    const stats8 = new Statistics(8, 8, 'OCCUPIED', new Date('2020-01-06 07:00'));
    const stats9 = new Statistics(9, 4, 'FREE', new Date('2020-01-07 08:00:00'));
    const stats10 = new Statistics(10, 9, 'OCCUPIED', new Date('2020-01-07 08:04'));
    const stats11 = new Statistics(11, 5, 'FREE', new Date('2020-01-06 08:05'));
    const stats12 = new Statistics(12, 3, 'FREE', new Date('2020-01-07 08:10'));
    const stats13 = new Statistics(13, 5, 'OCCUPIED', new Date('2020-01-07 12:00'));
    const stats14 = new Statistics(14, 7, 'OCCUPIED', new Date('2020-01-07 13:00'));

    this.statistics.push(stats1);
    this.statistics.push(stats2);
    this.statistics.push(stats3);
    this.statistics.push(stats4);
    this.statistics.push(stats5);
    this.statistics.push(stats6);
    this.statistics.push(stats7);
    this.statistics.push(stats8);
    this.statistics.push(stats9);
    this.statistics.push(stats10);
    this.statistics.push(stats11);
    this.statistics.push(stats12);
    this.statistics.push(stats13);
    this.statistics.push(stats14);



   }

   getAllParkingLots(): Observable<Array<ParkingLot>> {
     return of(this.parkingLots);
   }

   getAllStats(): Observable<Array<Statistics>> {
     return of(this.statistics);
   }
}
