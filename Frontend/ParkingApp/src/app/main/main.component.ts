import { Component, OnInit, OnDestroy } from '@angular/core';
import { DataService } from '../data.service';
import { ParkingLot } from '../Model/ParkingLot';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

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
    this.router.navigate(['']);
  }

  showDetails(id: number) {
    this.router.navigate([''], {queryParams : {id , action : 'view'}});
    this.selectedParkingLot = this.parkingLots.find(pl => pl.id === id);
    this.processUrlParams();
  }

}
