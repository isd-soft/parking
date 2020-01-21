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
  confirmPass: string;

  errorMessage = 'Invalid Credentials';
  successMessage: string;
  invalidLogin = false;
  loginSuccess = false;

  @Output()
  userRegEvent = new EventEmitter();

  private userForm: FormGroup;

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

    /* password regexp validation*/
    function forbiddenPassValidator(nameRe: RegExp): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const forbidden = nameRe.test(control.value);
        return !forbidden ? {forbiddenPass: {value: control.value}} : null;
      };
    }

    this.userForm = new FormGroup({
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
        * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"
        */

        forbiddenPassValidator(/"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"/gi)
      ])
    });

  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    const pass = group.get('password').value;
    const confirmPass = group.get('confirmPass').value;

    return pass === confirmPass ? null : { notSame: true };
  }

  get name() {
    return this.userForm.get('username');
  }

  get pass() {
    return this.userForm.get('password');
  }

  get passConf() {
    return this.userForm.get('confirmPass');
  }

  onSubmit() {
    this.handleRegistration();
  }

  handleRegistration() {
    // TODO: registration logic

    // this.authenticationService.authenticationService(this.username, this.password).subscribe((result) => {
    //   this.invalidLogin = false;
    //   this.loginSuccess = true;
    //   this.successMessage = 'Login Successful.';
    //   this.router.navigate(['/parking']);
    // }, () => {
    //   this.invalidLogin = true;
    //   this.loginSuccess = false;
    // });
  }

  navigateToLogin() {
    this.router.navigate(['login']);
  }
}
