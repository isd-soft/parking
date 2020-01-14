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

  loadDataCounter = 0;


  constructor(private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.loadData();

    this.processUrlParams();

    this.updateSubscription = interval(5000).subscribe(
      () => {
        this.loadData();
      }
    );
  }

  ngOnDestroy() {
    this.updateSubscription.unsubscribe();
  }

  loadData() {
    this.dataService.getAllParkingLots().subscribe(
      data => {
        if (data.length !== 0) {
          this.parkingLots = data.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0));
          this.dataLoaded = true;
          this.message = '';

        } else {
          this.message = 'No data found, please contact support';
        }
      },
      error => {
        const subscribe: Subscription = interval(10000).subscribe(
          () => {
            if (this.loadDataCounter <= 5) {
              this.loadData();
            } else {
              subscribe.unsubscribe();
              this.updateSubscription.unsubscribe();
              this.message = 'Can\'t connect to server. Please contact support';
            }
          }
        );
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
