import { Injectable } from '@angular/core';
import { ParkingLot } from './Model/ParkingLot';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  parkingLots: Array<ParkingLot>;

  constructor() {

    this.parkingLots = new Array<ParkingLot>();

    const pl1 = new ParkingLot();
    pl1.id = 1;
    pl1.name = 'lot 01';
    pl1.isFree = false;
    pl1.dateTime = new Date();

    this.parkingLots.push(pl1);

    const pl2 = new ParkingLot();
    pl2.id = 2;
    pl2.name = 'lot 02';
    pl2.isFree = false;
    pl2.dateTime = new Date();

    this.parkingLots.push(pl2);

    const pl3 = new ParkingLot();
    pl3.id = 3;
    pl3.name = 'lot 03';
    pl3.isFree = true;
    pl3.dateTime = new Date();

    this.parkingLots.push(pl3);

    const pl4 = new ParkingLot();
    pl4.id = 4;
    pl4.name = 'lot 04';
    pl4.isFree = true;
    pl4.dateTime = new Date();

    this.parkingLots.push(pl4);

    const pl5 = new ParkingLot();
    pl5.id = 5;
    pl5.name = 'lot 05';
    pl5.isFree = false;
    pl5.dateTime = new Date();

    this.parkingLots.push(pl5);

    const pl6 = new ParkingLot();
    pl6.id = 6;
    pl6.name = 'lot 06';
    pl6.isFree = true;
    pl6.dateTime = new Date();

    this.parkingLots.push(pl6);

    const pl7 = new ParkingLot();
    pl7.id = 7;
    pl7.name = 'lot 07';
    pl7.isFree = false;
    pl7.dateTime = new Date();

    this.parkingLots.push(pl7);

    const pl8 = new ParkingLot();
    pl8.id = 8;
    pl8.name = 'lot 08';
    pl8.isFree = true;
    pl8.dateTime = new Date();

    this.parkingLots.push(pl8);

    const pl9 = new ParkingLot();
    pl9.id = 9;
    pl9.name = 'lot 09';
    pl9.isFree = true;
    pl9.dateTime = new Date();

    this.parkingLots.push(pl9);

    const pl10 = new ParkingLot();
    pl10.id = 10;
    pl10.name = 'lot 10';
    pl10.isFree = false;
    pl10.dateTime = new Date();

    this.parkingLots.push(pl10);
   }

   getAllParkingLots(): Observable<Array<ParkingLot>> {
     return of(this.parkingLots);
   }
}
