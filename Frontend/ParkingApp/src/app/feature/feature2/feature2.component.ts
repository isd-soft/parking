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

  numberOfParkingLots: Array<number> = new Array<number>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

  parkingLots: Array<ParkingLot>;

  selectedParkingLot: ParkingLot;

  action: string;

  dataLoaded = false;

  message = 'Please wait...';

  updateSubscription: Subscription;

  connectionLostSubscription: Subscription;

  loadDataSubscription: Subscription;

  loadDataCounter = 0;


  constructor(private dataService: DataService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.loadData();

    this.processUrlParams();

    this.updateSubscription = interval(3000).subscribe(
      () => {
        this.loadData();
      }
    );
  }

  ngOnDestroy() {
    this.updateSubscription.unsubscribe();
    this.loadDataSubscription.unsubscribe();
  }

  loadData() {
    this.loadDataSubscription = this.dataService.getAllParkingLots().subscribe(
      data => {
        if (data.length !== 0) {
          this.parkingLots = data.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0));
          this.dataLoaded = true;
          this.message = '';
          console.log('loadData');

          if (this.parkingLots.length < 10) {
            for (let i = 1; i <= 10; i++) {
              const pl = new ParkingLot();
              if (!this.parkingLots.find(lot => lot.number === i)) {
                pl.number = pl.id = i;
                this.parkingLots.push(pl);
              }
            }
          }
          this.loadDataCounter = 0;
        } else {
          this.message = 'No data found, please contact support';
        }
      },
      error => {
        setTimeout(() => {
          if (++this.loadDataCounter <= 5) {
            this.message = 'Connection lost. Please wait...';
            console.log(this.loadDataCounter);
            this.loadData();
          } else {
            this.message = 'Can\'t connect to server. Please contact support';
            this.updateSubscription.unsubscribe();
            this.loadDataSubscription.unsubscribe();
          }
        }, 7000);
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
