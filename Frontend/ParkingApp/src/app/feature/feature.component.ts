import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { ParkingLot } from '../Model/ParkingLot';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-feature',
  templateUrl: './feature.component.html',
  styleUrls: ['./feature.component.css']
})
export class FeatureComponent implements OnInit {

  noData: Array<number>;

  otherParkingLots: Array<number>;

  parkingLots: Array<ParkingLot>;

  selectedParkingLot: ParkingLot;

  action: string;

  dataLoaded = false;


  message = 'Please wait...';

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
    this.router.navigate(['test']);
  }

  showDetails(id: number) {
    this.router.navigate(['test'], {queryParams : {id , action : 'view'}});
    this.selectedParkingLot = this.parkingLots.find(pl => pl.id === id);
    this.processUrlParams();
  }

}
