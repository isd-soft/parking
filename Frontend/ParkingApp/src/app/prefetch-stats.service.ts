import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { Statistics } from './Model/Statistics';
import { DataService } from './data.service';

@Injectable({
  providedIn: 'root'
})
export class PrefetchStatsService implements Resolve<Observable<Array<Statistics>>> {


  constructor(private dataService: DataService) { }

  resolve() {
    return this.dataService.getAllStats();
  }
}
