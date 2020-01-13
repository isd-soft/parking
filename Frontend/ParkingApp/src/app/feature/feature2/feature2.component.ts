import { Component, OnInit, OnDestroy } from '@angular/core';
import { ParkingLot } from 'src/app/Model/ParkingLot';
import { Subscription, interval } from 'rxjs';
import { DataService } from 'src/app/data.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-feature2',
  templateUrl: './feature2.component.html',
  styleUrls: ['./feature2.component.css']
})
export class Feature2Component implements OnInit, OnDestroy {

  noData: Array<number>;

  otherParkingLots: Array<number>;

  parkingLots: Array<ParkingLot>;

  selectedParkingLot: ParkingLot;

  action: string;

  dataLoaded = false;


  message = 'Please wait...';

  updateSubscription: Subscription;

  iterator = 0;

  constructor(private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.loadData();
    this.noData = new Array<number>();
    for (let i = 1; i <= 10; i++) {
      this.noData.push(i);
    }

    this.otherParkingLots = new Array<number>();
    for (let i = 1; i <= 7; i++) {
      this.otherParkingLots.push(i);
    }

    this.processUrlParams();

    this.updateSubscription = interval(1000).subscribe(
      () => {
        this.loadData();
        console.log('loadData: ' + (++this.iterator));
      }
    );
  }

  ngOnDestroy() {
    this.updateSubscription.unsubscribe();
  }

  loadData() {
    this.dataService.getAllParkingLots().subscribe(
      data => {
        this.parkingLots = data.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0));
        this.dataLoaded = true;
        this.message = '';
      },
      error => {
        this.parkingLots = null;
      }
    );
  }

  processUrlParams() {
    this.route.queryParams.subscribe(
      // tslint:disable-next-line: no-string-literal
      params => this.action = params['action']
    );
  }

  refresh() {
    this.loadData();
    this.router.navigate(['test2']);
  }

  showDetails(id: number) {
    this.router.navigate(['test2'], {queryParams : {id , action : 'view'}});
    this.selectedParkingLot = this.parkingLots.find(pl => pl.id === id);
    this.processUrlParams();
  }

}
