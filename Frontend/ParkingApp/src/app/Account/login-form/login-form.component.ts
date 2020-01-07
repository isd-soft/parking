import { Component, OnInit, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  @Output()
  userLoginEvent = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  onSubmit() {
    this.userLoginEvent.emit();
  }

}
