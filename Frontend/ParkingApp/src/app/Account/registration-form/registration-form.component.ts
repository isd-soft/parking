import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../auth.service';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';

@Component({
  selector: 'app-reg-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegFormComponent implements OnInit {

  username: string;
  password: string;
  passConfirm: string;

  errorMessage = 'Invalid Credentials';
  successMessage: string;

  invalidReg = false;
  regSuccess = false;

  @Output()
  userRegEvent = new EventEmitter();

  private regForm: FormGroup;

  submitted = false;

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

    this.regForm = new FormGroup({
      username: new FormControl(this.username, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(15),

        /*
        * ^(?=.{6,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$
           └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
                 │         │         │            │           no _ or . at the end
                 │         │         │            allowed characters
                 │         │         no __ or _. or ._ or .. inside
                 │         no _ or . at the beginning
                 username is 5-15 characters long
        */

        // Validators.pattern('^(?=.{5,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$')],

        forbiddenNameValidator(/^(?=.{5,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/i)
      ]),

      password: new FormControl(this.password, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(10),

        /*
        Password pattern

         Minimum 6 characters, at least one uppercase letter, one lowercase letter, one number and one special character

        * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$"
        */

        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$')
      ]),

      passConfirm: new FormControl(this.passConfirm, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(10),
      ])
    });
  }

  get name() {
    return this.regForm.get('username');
  }

  get pass() {
    return this.regForm.get('password');
  }

  get passConf() {
    return this.regForm.get('passConfirm');
  }

  onSubmit() {
    console.log(this.username + '  ' + this.password);
    this.submitted = true;
    this.handleRegistration();
  }

  handleRegistration() {

    this.authenticationService.authenticationServiceRegistration(this.username, this.password).subscribe(
      data => {

        console.log('Registration in authenticationService.Registration.');
        console.log('Server response: ' + data);

        if (data) {
          this.invalidReg = false;
          this.regSuccess = true;
          this.successMessage = 'Registration Successful.';
          console.log(this.successMessage);
          alert(this.successMessage);

          this.navigateToLogin();
        } else {
          this.invalidReg = true;
          this.regSuccess = false;

          console.log(this.successMessage);
        }

      }, error => {
        console.log(error);
        alert('Registration failed.');
        console.log('Registration failed.');
      });
  }

  navigateToLogin() {
    this.router.navigate(['login'], {queryParams: {action: 'login'}});
  }
}
