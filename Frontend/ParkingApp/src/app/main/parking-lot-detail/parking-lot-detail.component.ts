import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ParkingLot} from 'src/app/Model/ParkingLot';
import {ActivatedRoute} from '@angular/router';
import {AuthenticationService} from '../../Account/auth.service';

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
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {

    // testing
    this.isAdminLoggedIn();

    this.route.queryParams.subscribe(
      params => {
        this.action = params['action'];
      }
    );
  }

  goBack() {
    this.goBackEvent.emit();
  }

  booking() {
    this.bookingEvent.emit();
  }

  isAdminLoggedIn() {
    return this.authenticationService.isAdminLoggedIn();
  }

  isUserLoggedIn() {
    return this.authenticationService.isUserLoggedIn();
  }

}
