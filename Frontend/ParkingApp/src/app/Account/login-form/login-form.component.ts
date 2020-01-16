import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { DataService } from 'src/app/data.service.local';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  @Output()
  userLoginEvent = new EventEmitter();

  userLogin: string;
  userPassword: string;

  constructor(private dataService: DataService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.userLoginEvent.emit();
  }

}
