import {Attribute, Directive, forwardRef} from '@angular/core';
import {AbstractControl, NG_VALIDATORS, Validator} from '@angular/forms';

@Directive({
  // tslint:disable-next-line:directive-selector
  selector: '[validateEqual][formControlName],[validateEqual][formControl],[validateEqual][ngModel]',
  providers: [
    {provide: NG_VALIDATORS, useExisting: forwardRef(() => EqualValidator), multi: true}
  ]
})
export class EqualValidator implements Validator {

  constructor(@Attribute('validateEqual') public validateEqual: string,
              @Attribute('reverse') public reverse: string) {
  }

  private get isReverse() {
    if (!this.reverse) {
      return false;
    }
    return this.reverse === 'true';
  }

  validate(control: AbstractControl): { [key: string]: any } {
    // self value
    const value = control.value;

    // control value
    const controlValue = control.root.get(this.validateEqual);

    // value not equal
    if (controlValue && value !== controlValue.value && !this.isReverse) {
      return {
        validateEqual: false
      };
    }

    // value equal and reverse
    if (controlValue && value === controlValue.value && this.isReverse) {
      delete controlValue.errors.validateEqual;
      if (!Object.keys(controlValue.errors).length) {
        controlValue.setErrors(null);
      }
    }

    // value not equal and reverse
    if (controlValue && value !== controlValue.value && this.isReverse) {
      controlValue.setErrors({
        validateEqual: false
      });
    }

    return null;
  }
}
