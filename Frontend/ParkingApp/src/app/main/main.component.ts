import {Component, OnInit} from '@angular/core';
import {DataService} from '../data.service';
import {ParkingLot} from '../Model/ParkingLot';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  parkingLots: Array<ParkingLot>;
  selectedParkingLot: ParkingLot;
  action: string;

  noData: Array<number>;

  constructor(private dataService: DataService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.noData = new Array<number>();
    for (let i = 1; i <= 10; i++) {
      this.noData.push(i);
    }
    this.loadData();
    this.processUrlParams();
  }

  loadData() {
    this.dataService.getAllParkingLots().subscribe(
      data => {
        if (data.length !== 0) {
          this.parkingLots = data.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0));
        } else {
          this.parkingLots = null;
        }

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
    this.router.navigate(['']);
  }

  showDetails(id: number) {
    this.router.navigate([''], {queryParams : {id , action : 'view'}});
    this.selectedParkingLot = this.parkingLots.find(pl => pl.id === id);
    this.processUrlParams();
  }
}
