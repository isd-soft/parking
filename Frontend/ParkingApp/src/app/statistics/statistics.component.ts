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
  filteredStatistics: Array<Statistics>;
  parkingLots: Array<ParkingLot>;
  selectedLotNumber = null;

  startDate: string;
  endDate: string;

  lotSortedAsc: boolean;
  lotSortedDesc: boolean;

  dateSortedAsc: boolean;
  dateSortedDesc: boolean;



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

    this.filteredStatistics = this.statistics;
    this.selectedLotNumber = undefined;

    this.startDate = formatDate('2020-01-01', 'yyyy-MM-dd', 'en-UK');
    this.endDate = formatDate(new Date(), 'yyyy-MM-dd', 'en-UK');

    this.lotSortedAsc = false;
    this.lotSortedDesc = false;

    this.dateSortedAsc = false;
    this.dateSortedDesc = false;
  }

  filterData() {
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
    this.filteredStatistics = tempStats;
  }

  sortTableByLotNumber() {

    this.lotSortedAsc = true;
    this.lotSortedDesc = true;


    for (let i = 0; i < this.filteredStatistics.length - 1; i++) {

      if (this.filteredStatistics[i].parkingLotNumber > this.filteredStatistics[i + 1].parkingLotNumber) {
        this.lotSortedAsc = false;
      }

      if (this.filteredStatistics[i].parkingLotNumber < this.filteredStatistics[i + 1].parkingLotNumber) {
        this.lotSortedDesc = false;
      }
    }


    if (this.lotSortedAsc) {
      this.filteredStatistics.sort(
        (a, b) => a.parkingLotNumber < b.parkingLotNumber ? 1 : (a.parkingLotNumber > b.parkingLotNumber ? -1 : 0));

      this.lotSortedAsc = false;
      this.lotSortedDesc = true;
    } else {
      this.filteredStatistics.sort(
        (a, b) => a.parkingLotNumber > b.parkingLotNumber ? 1 : (a.parkingLotNumber < b.parkingLotNumber ? -1 : 0));

      this.lotSortedDesc = false;
      this.lotSortedAsc = true;
    }

    this.dateSortedAsc = false;
    this.dateSortedDesc = false;
  }

  sortTableByDate() {
    this.dateSortedDesc = true;
    this.dateSortedAsc = true;

    for (let i = 0; i < this.filteredStatistics.length - 1; i++) {

      if (this.filteredStatistics[i].updatedAt > this.filteredStatistics[i + 1].updatedAt) {
        this.dateSortedAsc = false;
      }

      if (this.filteredStatistics[i].updatedAt < this.filteredStatistics[i + 1].updatedAt) {
        this.dateSortedDesc = false;
      }
    }

    if (this.dateSortedAsc) {
      this.filteredStatistics.sort(
        (a, b) => a.updatedAt < b.updatedAt ? 1 : (a.updatedAt > b.updatedAt ? -1 : 0)
      );

      this.dateSortedAsc = false;
      this.dateSortedDesc = true;
    } else {
      this.filteredStatistics.sort(
        (a, b) => a.updatedAt > b.updatedAt ? 1 : (a.updatedAt < b.updatedAt ? -1 : 0)
      );

      this.dateSortedDesc = false;
      this.dateSortedAsc = true;
    }

    this.lotSortedAsc = false;
    this.lotSortedDesc = false;
  }
}
