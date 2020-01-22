import {AbstractControl} from '@angular/forms';

// fallback service confirm repeat password
export class ConfirmPasswordValidator {
  static MatchPassword(control: AbstractControl) {

    const password = control.get('password').value;
    const confirmPassword = control.get('passConfirm').value;

    if (password !== confirmPassword) {
      control.get('passConfirm').setErrors({ConfirmPassword: true});
    } else {
      return null;
    }
  }
}
