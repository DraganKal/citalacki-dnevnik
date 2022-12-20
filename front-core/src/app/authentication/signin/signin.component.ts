import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "src/app/core/service/auth.service";
import { Role } from "src/app/core/models/role";
import { UnsubscribeOnDestroyAdapter } from "src/app/shared/UnsubscribeOnDestroyAdapter";
import { AuthenticationService } from "src/app/core/service/authentication.service";
import { UserService } from "src/app/core/service/user.service";
import { LanguageService } from "src/app/core/service/language.service";
@Component({
  selector: "app-signin",
  templateUrl: "./signin.component.html",
  styleUrls: ["./signin.component.scss"],
})
export class SigninComponent
  implements OnInit
{
  authForm: FormGroup;
  hide = true;
  loading = false;
  error = "";

  constructor(private formBuilder: FormBuilder, 
    private authenticationService: AuthenticationService, 
    private userService: UserService,
    private router: Router,
    private languageService: LanguageService) { 
    this.authForm = this.formBuilder.group({
      username: ['', []],
      password: ['', []]
    });
  }

  ngOnInit(): void {
    if (localStorage.getItem('token') && localStorage.getItem('user')) {
      this.router.navigate(["/extra-pages/blank"]);
    }
    if (!localStorage.getItem('lang')) {
      this.languageService.setLanguage('de');
    }

  }

  onSubmit() {
    // this.loading = true;
    const formData = this.authForm.getRawValue();
    this.authenticationService.login(formData).subscribe((response: any) => {
      this.authenticationService.setToken(response.token);
      this.userService.setUser(response.user);
      this.router.navigate(["/extra-pages/blank"]);
        // this.loading = false;
      });

  }

  translatePage(lang: string) {
    this.languageService.setLanguage(lang);
  }

}
