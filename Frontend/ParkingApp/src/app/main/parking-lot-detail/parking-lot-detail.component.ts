import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ParkingLot } from 'src/app/Model/ParkingLot';
import { ActivatedRoute } from '@angular/router';

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

  action: string;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe(
      params => {
        this.action = params['action'];
      }
    );
  }

  goBack() {
    this.goBackEvent.emit();
  }

}
