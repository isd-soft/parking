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
    if (this.username !== '' && this.password !== '') {

      console.log(this.username + '  ' + this.password);

      // for local login test
      // this.localLoginTest();

      // backend http auth
      this.handleLogin();
    }
  }

  handleLogin() {

    this.authenticationService.login(this.username, this.password).subscribe(
      data => {

        console.log('Authentication success in authenticationService.login.');
        console.log('Server response: ' + data);

        if (data) {
          this.invalidLogin = false;
          this.loginSuccess = true;
          this.successMessage = 'Login Successful.';
          console.log(this.successMessage);

          /*sessionStorage.setItem(
            'token',
            btoa(this.username + ':' + this.password)
          );*/

          this.authenticationService.registerSuccessfulLogin(this.username);
          console.log(sessionStorage.getItem('authenticatedUser'));

          this.router.navigate(['']);
        } else {
          this.invalidLogin = true;
          this.loginSuccess = false;

          console.log(this.successMessage);
        }

      }, error => {
        alert('Authentication failed.');
        console.log('Authentication failed.');
      });

    // 1st variant not properly working
    /*this.authenticationService.authenticationServiceLogin(this.username, this.password).subscribe((result) => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
      this.router.navigate(['']);
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });*/

    // 2nd variant not properly working
    /*this.authenticationService.login(this.username, this.password).subscribe(isValid => {
      if (isValid) {
        this.invalidLogin = false;
        this.loginSuccess = true;
        this.successMessage = 'Login Successful.';

        sessionStorage.setItem(
          'token',
          btoa(this.username + ':' + this.password)
        );

        this.authenticationService.registerSuccessfulLogin(this.username);
        this.router.navigate(['']);
      } else {
        alert('Authentication failed.');
      }
    });*/
  }

  private localLoginTest() {
    // only for testing purpose
    if ((this.username === this.authenticationService.admin && this.password === this.authenticationService.adminPassword)
      || (this.username === this.authenticationService.user && this.password === this.authenticationService.userPassword)
    ) {
      // credentials error handle
      this.invalidLogin = false;
      this.loginSuccess = true;

      this.authenticationService.registerSuccessfulLogin(this.username);

      console.log(sessionStorage.getItem('authenticatedUser'));

      this.router.navigate(['']);
      this.userLoginEvent.emit();
    } else {
      this.invalidLogin = true;
      this.loginSuccess = false;
    }
  }

  navigateToRegistration() {
    this.router.navigate(['registration']);
  }
}
