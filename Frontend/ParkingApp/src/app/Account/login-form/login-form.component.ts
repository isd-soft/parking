import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../auth.service';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  username: string;
  password: string;
  errorMessage = 'Invalid Credentials';
  successMessage: string;

  invalidLogin = false;
  loginSuccess = false;

  @Output()
  userLoginEvent = new EventEmitter();

  private loginForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {

    /* username regexp validation*/
    function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const forbidden = nameRe.test(control.value);
        return !forbidden ? {forbiddenName: {value: control.value}} : null;
      };
    }

    // not working
    /* password regexp validation*/
    /*function forbiddenPassValidator(passRe: RegExp): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const forbidden = passRe.test(control.value);
        return !forbidden ? {forbiddenPass: {value: control.value}} : null;
      };
    }*/

    this.loginForm = new FormGroup({
      username: new FormControl(this.username, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(15),

        // <-- Here's how you pass in the custom validator.

        /*
        * ^(?=.{6,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$
           └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
                 │         │         │            │           no _ or . at the end
                 │         │         │            allowed characters
                 │         │         no __ or _. or ._ or .. inside
                 │         no _ or . at the beginning
                 username is 5-15 characters long
        */

        forbiddenNameValidator(/^(?=.{5,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/i)
      ]),

      password: new FormControl(this.password, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(10),

        /*
        Minimum 6 characters, at least one uppercase letter, one lowercase letter, one number and one special character

        * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$"
        */

        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$')
      ])
    });
  }

  get name() {
    return this.loginForm.get('username');
  }

  get pass() {
    return this.loginForm.get('password');
  }

  onSubmit() {
    // local mockup for test
    const user = 'admin';
    const pass = 'aRduin1$';

    if (this.username === user && this.password === pass) {

      console.log(this.username + '  ' + this.password);

      this.router.navigate(['parking']);
      this.userLoginEvent.emit();
    }

    // uncomment this for backend request
    /*this.handleLogin();

    this.userLoginEvent.emit();*/
  }

  handleLogin() {
    this.authenticationService.authenticationServiceLogin(this.username, this.password).subscribe((result) => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
      this.router.navigate(['parking']);
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });
  }

  navigateToRegistration() {
    this.router.navigate(['registration']);
  }
}
