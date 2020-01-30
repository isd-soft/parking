import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  currentPage: string;

  constructor(private router: Router) { }

  ngOnInit() {
    this.currentPage = this.router.url;
  }

  navigateToMain() {
    this.router.navigate(['']);
    this.currentPage = '/';
  }

  navigateToStats() {
    this.router.navigate(['stats']);
    this.currentPage = '/stats';

  }

}
