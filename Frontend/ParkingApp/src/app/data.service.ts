import { Injectable } from '@angular/core';
import { ParkingLot } from './Model/ParkingLot';
import { Observable} from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Statistics } from './Model/Statistics';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

   getAllParkingLots(): Observable<Array<ParkingLot>> {
     return this.http.get<Array<ParkingLot>>(environment.restUrl + '/parking')
     .pipe(
       map(
         data => data
          .map(
            pl => ParkingLot.fromHttp(pl)
         )
       )
     );
   }

   getAllStats(): Observable<Array<Statistics>> {
     return this.http.get<Array<Statistics>>(environment.restUrl + '/stats')
     .pipe(
       map(
         data => data
          .map(
            st => Statistics.fromHttp(st)
         )
       )
     );
   }
}
