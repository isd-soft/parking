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
    pl1.id = 'f9aa68b9-779d-4853-8277-4f7120c183d1';
    pl1.number = '01';
    pl1.status = 'FREE';
    pl1.date = new Date();

    this.parkingLots.push(pl1);

    const pl2 = new ParkingLot();
    pl2.id = 'f9aa68b9-779d-4853-8277-4f7120c183d2';
    pl2.number = '02';
    pl2.status = 'FREE';
    pl2.date = new Date();

    this.parkingLots.push(pl2);

    const pl3 = new ParkingLot();
    pl3.id = 'f9aa68b9-779d-4853-8277-4f7120c183d3';
    pl3.number = '03';
    pl3.status = 'OCCUPIED';
    pl3.date = new Date();

    this.parkingLots.push(pl3);

    const pl4 = new ParkingLot();
    pl4.id = 'f9aa68b9-779d-4853-8277-4f7120c183d4';
    pl4.number = '04';
    pl4.status = 'UNKNOWN';
    pl4.date = new Date();

    this.parkingLots.push(pl4);

    const pl5 = new ParkingLot();
    pl5.id = 'f9aa68b9-779d-4853-8277-4f7120c183d5';
    pl5.number = '05';
    pl5.status = 'OCCUPIED';
    pl5.date = new Date();

    this.parkingLots.push(pl5);

    const pl6 = new ParkingLot();
    pl6.id = 'f9aa68b9-779d-4853-8277-4f7120c183d6';
    pl6.number = '06';
    pl6.status = 'FREE';
    pl6.date = new Date();

    this.parkingLots.push(pl6);

    const pl7 = new ParkingLot();
    pl7.id = 'f9aa68b9-779d-4853-8277-4f7120c183d7';
    pl7.number = '07';
    pl7.status = 'FREE';
    pl7.date = new Date();

    this.parkingLots.push(pl7);

    const pl8 = new ParkingLot();
    pl8.id = 'f9aa68b9-779d-4853-8277-4f7120c183d8';
    pl8.number = '08';
    pl8.status = 'OCCUPIED';
    pl8.date = new Date();

    this.parkingLots.push(pl8);

    const pl9 = new ParkingLot();
    pl9.id = 'f9aa68b9-779d-4853-8277-4f7120c183d9';
    pl9.number = '09';
    pl9.status = 'FREE';
    pl9.date = new Date();

    this.parkingLots.push(pl9);

    const pl10 = new ParkingLot();
    pl10.id = 'f9aa68b9-779d-4853-8277-4f7120c183d0';
    pl10.number = '10';
    pl10.status = 'OCCUPIED';
    pl10.date = new Date();

    this.parkingLots.push(pl10);
   }

   getAllParkingLots(): Observable<Array<ParkingLot>> {
     return of(this.parkingLots);
   }
}
