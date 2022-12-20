import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/core/service/user.service';
import { UtilService } from 'src/app/main/util.service';
import { LanguageService } from 'src/app/core/service/language.service';
@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit {
  authForm: FormGroup;
  submitted = false;
  returnUrl: string;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private utilService: UtilService,
    private languageService: LanguageService
  ) {}
  ngOnInit() {
    this.authForm = this.formBuilder.group({
      email: [
        '',
        [Validators.required, Validators.email, Validators.minLength(5)],
      ],
    });
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  get f() {
    return this.authForm.controls;
  }
  onSubmit() {
    this.submitted = true;
    // stop here if form is invalid
    if (this.authForm.invalid) {
      return;
    } else {
      
      let email = this.authForm.controls['email'].value;
      let emailDTO = {
        email: email
      }

      this.userService.sendResetPasswordLink(emailDTO).subscribe(() => {
        this.utilService.showNotification(
          "snackbar-success",
          "Password link sent Successfully!",
          "bottom",
          "center"
        );
        setTimeout(() => {
          this.router.navigateByUrl("authentication/signin");
        }, 3000);
      })
    }
  }

  translatePage(lang: string) {
    this.languageService.setLanguage(lang);
  }

}
