import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Statistics } from '../Model/Statistics';
import { formatDate } from '@angular/common';
import { ParkingLot } from '../Model/ParkingLot';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  statistics: Array<Statistics>;
  sortedStatistics: Array<Statistics>;
  parkingLots: Array<ParkingLot>;
  selectedLotNumber = null;

  startDate: string;
  endDate: string;


  constructor(private dataService: DataService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.dataService.getAllStats().subscribe(
      data => this.statistics = data.sort((a, b) => a.updatedAt > b.updatedAt ? 1 : (a.updatedAt < b.updatedAt ? -1 : 0))
    );

    this.dataService.getAllParkingLots().subscribe(
      data => this.parkingLots = data.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0))
    );

    this.sortedStatistics = this.statistics;
    this.selectedLotNumber = undefined;

    this.startDate = formatDate('2020-01-01', 'yyyy-MM-dd', 'en-UK');
    this.endDate = formatDate(new Date(), 'yyyy-MM-dd', 'en-UK');
  }

  sortData() {
    let tempStats = new Array<Statistics>();

    if (this.selectedLotNumber === '-') {
      this.selectedLotNumber = null;
    }

    if (new Date(this.startDate).getDate() > new Date(this.endDate).getDate()) {
      alert('The start date you entered is higher that the end date');
    } else if ((this.startDate != null) && (this.endDate != null)) {
      tempStats = this.statistics
      .filter(st => st.updatedAt.getDate() >= new Date(this.startDate).getDate()
        && st.updatedAt.getDate() <= new Date(this.endDate).getDate());

      if (this.selectedLotNumber != null) {
        tempStats = tempStats
          .filter(st => st.parkingLotNumber === +this.selectedLotNumber);
      }

    }
    this.sortedStatistics = tempStats;
  }
}
