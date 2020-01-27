import {Component, OnDestroy, OnInit} from '@angular/core';
import {DataService} from '../data.service';
import {ParkingLot} from '../Model/ParkingLot';
import {ActivatedRoute, Router} from '@angular/router';
import {interval, Subscription} from 'rxjs';

@Component({
  selector: 'app-feature',
  templateUrl: './feature.component.html',
  styleUrls: ['./feature.component.css']
})
export class FeatureComponent implements OnInit, OnDestroy {


  noData: Array<number>;

  otherParkingLots: Array<number>;

  parkingLots: Array<ParkingLot>;

  selectedParkingLot: ParkingLot;

  action: string;

  dataLoaded = false;

  message = 'Please wait...';

  updateSubscription: Subscription;

  connectionLostSubscription: Subscription;

  loadDataCounter = 0;


  constructor(private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) {
  }

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
          this.parkingLots = data;
          this.dataLoaded = true;
          this.message = '';

          if (this.parkingLots.length < 10) {
            for (let i = 1; i <= 10; i++) {
              const pl = new ParkingLot();
              if (!this.parkingLots.find(lot => lot.number === i)) {
                pl.number = pl.id = i;
                this.parkingLots.push(pl);
              }
            }
          }
          this.parkingLots.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0));
          if (this.connectionLostSubscription) {
            this.connectionLostSubscription.unsubscribe();
          }
        } else {
          this.message = 'No data found, please contact support';
        }
      },
      error => {
        this.message = 'Please wait...';
        this.connectionLostSubscription = interval(5000).subscribe(
          () => {
            if (this.loadDataCounter <= 5) {
              this.loadData();
            } else {
              this.connectionLostSubscription.unsubscribe();
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
    this.router.navigate(['test']);
  }

  showDetails(id: number) {
    this.router.navigate(['test'], {queryParams: {id, action: 'view'}});
    this.selectedParkingLot = this.parkingLots.find(pl => pl.id === id);
    this.processUrlParams();
  }
}
