import { Injectable } from '@angular/core';
import { ParkingLot } from './Model/ParkingLot';
import { Observable} from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

   getAllParkingLots(): Observable<Array<ParkingLot>> {
     console.log(this.http.get<Array<ParkingLot>>(environment.restUrl + '/api/v1/parking'));
     return this.http.get<Array<ParkingLot>>(environment.restUrl + '/api/v1/parking')
     .pipe(
       map(
         data => data.map(
           pl => ParkingLot.fromHttp(pl)
         )
       )
     );
   }
}
