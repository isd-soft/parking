import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ParkingLot} from 'src/app/Model/ParkingLot';
import {ActivatedRoute} from '@angular/router';

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
  isAdmin: boolean;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {

    // testing
    this.isAdmin = true;

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

}
