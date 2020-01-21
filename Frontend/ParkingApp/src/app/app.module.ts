import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';

import {AppComponent} from './app.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {MainComponent} from './main/main.component';
import {MenuComponent} from './menu/menu.component';
import {ParkingLotDetailComponent} from './main/parking-lot-detail/parking-lot-detail.component';
import {LoginFormComponent} from './Account/login-form/login-form.component';
import {StatisticsComponent} from './statistics/statistics.component';
import {FeatureComponent} from './feature/feature.component';
import {PrefetchStatsService} from './prefetch-stats.service';
import {PrefetchParkingLotsService} from './prefetch-parking-lots.service';
import {Feature2Component} from './feature/feature2/feature2.component';
import {ParkingLayoutComponent} from './main/parking-layout/parking-layout.component';
import {RegFormComponent} from './Account/registration-form/registration-form.component';

const routes: Routes = [
  {path: 'test', component: FeatureComponent},
  {path: 'test2', component: Feature2Component},
  {path: 'layout', component: ParkingLayoutComponent},
  {
    path: 'stats',
    component: StatisticsComponent,
    resolve: {stats: PrefetchStatsService, parkingLots: PrefetchParkingLotsService}
  },

  {path: 'login', component: LoginFormComponent},
  {path: 'logout', component: LoginFormComponent},
  {path: 'registration', component: RegFormComponent},
  // {path: '', component: LoginFormComponent},

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
    LoginFormComponent,
    RegFormComponent,
    StatisticsComponent,
    FeatureComponent,
    Feature2Component,
    ParkingLayoutComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],

  exports: [RouterModule],

  providers: [],
  bootstrap: [AppComponent],

})
export class AppModule {
}
