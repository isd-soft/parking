import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
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

}
