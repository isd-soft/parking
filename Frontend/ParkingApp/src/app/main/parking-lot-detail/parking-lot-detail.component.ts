import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ParkingLot} from 'src/app/Model/ParkingLot';
import {ActivatedRoute} from '@angular/router';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {AuthenticationService} from '../../Account/auth.service';
import {HttpClient} from '@angular/common/http';
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-parking-lot-detail',
  templateUrl: './parking-lot-detail.component.html',
  styleUrls: ['./parking-lot-detail.component.css']
})
export class ParkingLotDetailComponent implements OnInit {

  @Input()
  parkingLot: ParkingLot;

  @Output()
  goBackEvent = new EventEmitter();

  @Output()
  bookingEvent = new EventEmitter();

  action: string;

  constructor(private route: ActivatedRoute,
              private authenticationService: AuthenticationService,
              private  http: HttpClient) {
  }

  ngOnInit() {

    // testing
    this.isAdminLoggedIn();

    this.route.queryParams.subscribe(
      params => {
        this.action = params.action;
      }
    );
  }

  goBack() {
    delay(500);
    this.goBackEvent.emit();
  }

  handleReservation() {

    const parkingLotNumber = this.parkingLot.number;
    const parkingLotStatus = this.parkingLot.status;

    if (parkingLotStatus === 'RESERVED') {
      this.unreservate(parkingLotNumber).subscribe(data => {

        if (data) {
          alert('Cancel reservation success.');
          this.bookingEvent.emit();
        } else {
          alert('Cancel reservation failed.');
        }

      }, error => {
        console.log(error);
        alert('Cancel reservation failed.');
      });
    }

    if (parkingLotStatus === 'FREE') {
      this.reservate(parkingLotNumber).subscribe(data => {

        if (data) {
          alert('Reservation success.');
          this.bookingEvent.emit();
        } else {
          alert('Reservation failed.');
        }

      }, error => {
        console.log(error);
        alert('Reservation failed.');
      });
    }

    this.goBack();
  }

  reservate(parkingLotNumber: number) {

    const url = environment.restUrl + '/reservate/' + parkingLotNumber;

    console.log('Reservation... parking lot #' + parkingLotNumber);

    return this.http.get<Observable<boolean>>(url, {
      headers: {
        Accept: 'application/json',
        Authorization: 'Basic ' + sessionStorage.getItem('token')
      }
    });
  }

  unreservate(parkingLotNumber: number) {

    const url = environment.restUrl + '/unreservate/' + parkingLotNumber;

    console.log('Cancel reservation... parking lot #' + parkingLotNumber);

    return this.http.get<Observable<boolean>>(url, {
      headers: {
        Accept: 'application/json',
        Authorization: 'Basic ' + sessionStorage.getItem('token')
      }
    });
  }

  isAdminLoggedIn() {
    return this.authenticationService.isAdminLoggedIn();
  }

  isUserLoggedIn() {
    return this.authenticationService.isUserLoggedIn();
  }

  refresh(): void {
    window.location.reload();
  }
}
