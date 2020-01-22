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
  invalidLogin = false;
  loginSuccess = false;

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
    this.submitted = true;

    this.handleRegistration();
  }

  handleRegistration() {
    // TODO: registration logic

    /*this.authenticationService.authenticationService(this.username, this.password).subscribe((result) => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
      this.router.navigate(['/parking']);
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
    });*/
  }

  navigateToLogin() {
    this.router.navigate(['login'], {queryParams: {action: 'login'}});
  }
}
