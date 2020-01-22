import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Statistics } from '../Model/Statistics';
import { formatDate } from '@angular/common';
import { ParkingLot } from '../Model/ParkingLot';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  p: number = 1; // declaration of page index used for pagination
  colors = [{ status: "FREE", background: "#639063" }, { status: "OCCUPIED", background: "#cc6f6f" }, {status: "UNKNOWN", background: "gray"}]; // set the table row color depending on status
  statistics: Array<Statistics>;
  filteredStatistics: Array<Statistics>;
  parkingLots: Array<ParkingLot>;

  lotNumber = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

  selectedLotNumber: string = null;

  startDate: string;
  endDate: string;

  lotSortedAsc = false;
  lotSortedDesc = false;

  dateSortedAsc = false;
  dateSortedDesc = false;

  timeSortedAsc = false;
  timeSortedDesc = false;

  constructor(private dataService: DataService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    // tslint:disable: no-string-literal
    this.parkingLots = this.route.snapshot.data['parkingLots'];
    this.parkingLots.sort((a, b) => (a.number > b.number) ? 1 : (a.number < b.number ? -1 : 0));

    this.statistics = this.route.snapshot.data['stats'];
    this.statistics.sort((a, b) => a.updatedAt > b.updatedAt ? 1 : (a.updatedAt < b.updatedAt ? -1 : 0));


    this.filteredStatistics = this.statistics;
    this.selectedLotNumber = undefined;

    this.startDate = this.endDate = formatDate(new Date(), 'yyyy-MM-dd', 'en-UK');
    this.filterData();
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
          .filter(st => st.lotNumber === +this.selectedLotNumber);
      }

    }
    this.filteredStatistics = tempStats;
  }

  sortTableByLotNumber() {

    this.lotSortedAsc = true;
    this.lotSortedDesc = true;


    for (let i = 0; i < this.filteredStatistics.length - 1; i++) {

      if (this.filteredStatistics[i].lotNumber > this.filteredStatistics[i + 1].lotNumber) {
        this.lotSortedAsc = false;
      }

      if (this.filteredStatistics[i].lotNumber < this.filteredStatistics[i + 1].lotNumber) {
        this.lotSortedDesc = false;
      }
    }


    if (this.lotSortedAsc) {
      this.filteredStatistics.sort(
        (a, b) => a.lotNumber < b.lotNumber ? 1 : (a.lotNumber > b.lotNumber ? -1 : 0));

      this.lotSortedAsc = false;
      this.lotSortedDesc = true;
    } else {
      this.filteredStatistics.sort(
        (a, b) => a.lotNumber > b.lotNumber ? 1 : (a.lotNumber < b.lotNumber ? -1 : 0));

      this.lotSortedDesc = false;
      this.lotSortedAsc = true;
    }

    this.dateSortedAsc = false;
    this.dateSortedDesc = false;

    this.timeSortedDesc = false;
    this.timeSortedAsc = false;
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

    this.timeSortedDesc = false;
    this.timeSortedAsc = false;
  }

  sortTableByTime() {
    this.timeSortedDesc = true;
    this.timeSortedAsc = true;

    for (let i = 0; i < this.filteredStatistics.length - 1; i++) {

      if (this.filteredStatistics[i].updatedAt.getTime() > this.filteredStatistics[i + 1].updatedAt.getTime()) {
        this.timeSortedAsc = false;
      }

      if (this.filteredStatistics[i].updatedAt.getTime() < this.filteredStatistics[i + 1].updatedAt.getTime()) {
        this.timeSortedDesc = false;
      }
    }

    if (this.timeSortedAsc) {
      this.filteredStatistics.sort(
        (a, b) => a.updatedAt.getTime() < b.updatedAt.getTime() ? 1 : (a.updatedAt.getTime() > b.updatedAt.getTime() ? -1 : 0)
      );

      this.timeSortedAsc = false;
      this.timeSortedDesc = true;
    } else {
      this.filteredStatistics.sort(
        (a, b) => a.updatedAt.getTime() > b.updatedAt.getTime() ? 1 : (a.updatedAt.getTime() < b.updatedAt.getTime() ? -1 : 0)
      );

      this.timeSortedDesc = false;
      this.timeSortedAsc = true;
    }

    this.lotSortedAsc = false;
    this.lotSortedDesc = false;

    this.dateSortedDesc = false;
    this.dateSortedAsc = false;
  }

  getColor(status: string) {
    return this.colors.filter(item => item.status === status)[0].background;
  }
}


