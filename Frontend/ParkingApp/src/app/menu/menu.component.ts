import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../Account/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  userLogged: boolean;

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
  }

  navigateToLogin() {
    this.router.navigate(['login'], {queryParams: {action: 'login'}});
  }

  navigateToMain() {
    this.router.navigate(['']);
  }

  navigateToStats() {
    this.router.navigate(['stats']);
  }

  navigateTo404() {
    this.router.navigate(['404']);
  }

  navigateToTest() {
    this.router.navigate(['test']);
  }

  navigateToTest2() {
    this.router.navigate(['test2']);
  }

  navigateToLayout() {
    this.router.navigate(['layout']);
  }


  isAdminLoggedIn() {
    return this.authenticationService.isAdminLoggedIn();
  }

  isUserLoggedIn() {
    return this.authenticationService.isUserLoggedIn();
  }

  logout() {
    this.authenticationService.logout();
  }

  getUserName() {
    return this.authenticationService.getLoggedInUserName();
  }
}
