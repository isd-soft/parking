import { Injectable } from '@angular/core';
import { ParkingLot } from './Model/ParkingLot';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  parkingLots: Array<ParkingLot>;

  constructor() { }

   getAllParkingLots(): Observable<Array<ParkingLot>> {
     return of(null);
   }
}
