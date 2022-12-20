import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from 'src/app/core/service/authentication.service';
import { LanguageService } from 'src/app/core/service/language.service';
import { FuseTranslationLoaderService } from 'src/app/core/service/translation-loader.service';
import { UserService } from 'src/app/core/service/user.service';

@Component({
  selector: 'app-set-password',
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.sass']
})
export class SetPasswordComponent implements OnInit {

  setPasswordForm: FormGroup;
  matcher = new LogoassErrorStateMatcher();
  disableButton: boolean;
  tokenExpired: boolean;
  hide = true;
  message = "";

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private userService: UserService,
              private snackBar: MatSnackBar,
              private router: Router,
              private languageService: LanguageService) { 
    this.setPasswordForm = this.formBuilder.group(
      {
        token: ['', Validators.required],
        password: ['', Validators.compose([Validators.required,
          patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          patternValidator(/[a-z]/, {hasLowerCase: true}),
          patternValidator(/\d/, { hasNumber: true }),
          patternValidator(/[\\?#!$§-]/, { hasSpecialCharacter: true }),
          patternValidator(/^[^äöüÄÖÜß&+%]*$/, { umlautsSharpS: true }),
          patternValidator(/^[^ *]*$/, {hasSpace: true }),
          Validators.minLength(6),
          Validators.maxLength(20)
          ])
        ],
        confirmPassword: ['', Validators.required]
      },
      {validator: PasswordMatchValidator}
    );
  }

  ngOnInit(): void {
    this.splitToken();
  }

  splitToken() {
    // if (!localStorage.getItem("user")){
      this.authenticationService.logout();
      const urlArray = String(window.location.href).split("/");
      const token = urlArray[6];
      if (token != null && token !== '') {
        // this.authService.setSetPasswordToken(token);
        this.setPasswordForm.controls['token'].setValue(token);
        this.setPasswordForm.controls['token'].disable();
      }
      // this.authService.activateSetPassword();
      
  }

  translatePage(lang: string) {
    this.languageService.setLanguage(lang);
  }

  savePassword() {
    this.authenticationService.validateTokenForResetPassword(this.setPasswordForm.controls['token'].value)
      .subscribe(
        ok => {
          this.disableButton = true;
          const dto = {
            token: this.setPasswordForm.controls['token'].value,
            newPassword: this.setPasswordForm.controls['password'].value
          };
          this.userService.setPassword(dto).subscribe(
            data => {
              this.message = "SET_PASSWORD.PASSWORD_CHANGED_SUCCESSFULLY_INFO";
              this.showNotification(
                "snackbar-success",
                "Password changed Successfully!",
                "bottom",
                "center"
              );
              setTimeout(() => {
                this.router.navigateByUrl("authentication/signin");
              }, 3000);
            },
            er => {
              this.message = "SET_PASSWORD.TOKEN_EXPIRED_INFO";
              this.showNotification(
                "snackbar-danger",
                "TOKEN_EXPIRED",
                "bottom",
                "center"
              );
              this.disableButton = false;
              this.tokenExpired = true;
            }
          );
        },
        er => {
          this.message = "SET_PASSWORD.INVALID_TOKEN_INFO";
          this.showNotification(
            "snackbar-danger",
            "INVALID_TOKEN",
            "bottom",
            "center"
          );
          this.tokenExpired = true;
        }
      );
  }

  showNotification(colorName, text, placementFrom, placementAlign) {
    this.snackBar.open(text, "", {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }
}

export class LogoassErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    return (control.dirty || control.touched) && control.value !== control.parent.controls['password'].value;
  }
}

export function patternValidator (regex: RegExp, error: ValidationErrors): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } => {
    if (!control.value) {
      return null;
    }
    const valid = regex.test(control.value);
    return valid ? null : error;
  }
}

export const PasswordMatchValidator: ValidatorFn = (fg: FormGroup) => {
  const password = fg.controls['password'].value;
  const passwordConfirm = fg.controls['confirmPassword'].value;
  if(password === '')
    return {passwordMatch: true} ;
  return password !== passwordConfirm ? {passwordMatch: true} : null;
};