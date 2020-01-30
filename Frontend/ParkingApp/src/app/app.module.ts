import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {NgxPaginationModule} from 'ngx-pagination';
import {AppComponent} from './app.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {MainComponent} from './main/main.component';
import {MenuComponent} from './menu/menu.component';
import {ParkingLotDetailComponent} from './main/parking-lot-detail/parking-lot-detail.component';
import {StatisticsComponent} from './statistics/statistics.component';
import {PrefetchStatsService} from './prefetch-stats.service';
import {PrefetchParkingLotsService} from './prefetch-parking-lots.service';

const routes: Routes = [
  {path: 'stats', component: StatisticsComponent, resolve: {stats: PrefetchStatsService, parkingLots: PrefetchParkingLotsService}},
  {path: '', component: MainComponent},
  {path: '404', component: PageNotFoundComponent},
  {path: '**', redirectTo: '/404'}
];

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    MainComponent,
    MenuComponent,
    ParkingLotDetailComponent,
    StatisticsComponent
  ],

  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    NgxPaginationModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
