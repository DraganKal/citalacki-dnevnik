import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from 'src/app/core/service/authentication.service';
import { UserService } from 'src/app/core/service/user.service';
import { UtilService } from 'src/app/main/util.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.sass']
})
export class UserProfileComponent implements OnInit {

  user: any = {};
  userId: number;
  hide = true;
  matcher = new LogoassErrorStateMatcher();

  userForm: FormGroup;
  userPasswordForm: FormGroup;

  profileImage;
  maxSize=1048576*20;
  fileInput;

  constructor(private authenticationService: AuthenticationService,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private utilService: UtilService,
              private sanitizer: DomSanitizer,) { 
              }

  ngOnInit(): void {
    this.createForms();
    this.getCurrentUser();
  }

  getCurrentUser() {
    this.userId = this.authenticationService.getUser().id
    this.userService.getUserById(this.userId).subscribe((res) => {
      this.user = res;
      this.setFormValue();
      this.getProfileImage();
    })
  }

  createForms() {
    this.userForm = this.formBuilder.group({
      id:[''],
      firstName: [''],
      lastName: [''],
      email: ['', Validators.required],
      phone: [''],
      userType: ['', Validators.required],
      city: [''],
      street: [''],
      houseNumber: [''],
      zip:[''],
      profileImageUrl: ['']
    });

    this.userPasswordForm = this.formBuilder.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', Validators.compose([Validators.required,
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
      confirmPassword: ['', Validators.required],
    },
    {validator: PasswordMatchValidator});
  }

  setFormValue() {
    this.userForm.controls['id'].setValue(this.user.id);
    this.userForm.controls['firstName'].setValue(this.user.firstName);
    this.userForm.controls['lastName'].setValue(this.user.lastName);
    this.userForm.controls['email'].setValue(this.user.email);
    this.userForm.controls['phone'].setValue(this.user.phone);
    this.userForm.controls['userType'].setValue(this.user.userType);
    this.userForm.controls['city'].setValue(this.user.city);
    this.userForm.controls['street'].setValue(this.user.street);
    this.userForm.controls['houseNumber'].setValue(this.user.houseNumber);
    this.userForm.controls['zip'].setValue(this.user.zip);
  }

  getProfileImage() {
    this.userService.getProfileImage(this.userId).subscribe((response: any) => {
      if(response.size !== 0) {
        this.profileImage = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(response));
      }
    });
  }

  submitUpdate() {
    let user = this.userForm.getRawValue();
    this.userService.updateUser(user.id, user).subscribe((res) => {
      if(this.fileInput) {
        const fd = new FormData();
        fd.append('image', this.fileInput);
        this.userService.uploadProfileImage(this.userId, fd).subscribe(response => {
      
        });
      }
      this.utilService.showNotification(
        "snackbar-success",
        "Profile updated successfully!",
        "bottom",
        "center"
      );
      this.userService.setUser(res);
      location.reload();
    })
  }

  securityUserUpdate() {
    let userPassword = this.userPasswordForm.getRawValue();
    this.userService.updatePassword(this.userId, userPassword).subscribe({
      next: (res) => {
        this.userService.setUser(res);
        this.createForms();
        this.getCurrentUser();
        this.utilService.showNotification(
          "snackbar-success",
          "Password updated Successfully!",
          "bottom",
          "center"
        );
      },
      error: (error) =>{
        this.utilService.showNotification(
          "snackbar-danger",
          error.error.message,
          "bottom",
          "center"
        );
      }
    });
  }

  imageUpload(event) {
    if (event.target.files[0].size <= this.maxSize) {
      const urlCreator = window.URL;
      this.profileImage = this.sanitizer.bypassSecurityTrustUrl(
        urlCreator.createObjectURL(event.target.files[0]));
        this.fileInput = event.target.files[0];
    } 
  }

}

export class LogoassErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    return (control.dirty || control.touched) && control.value !== control.parent.controls['newPassword'].value;
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
  const password = fg.controls['newPassword'].value;
  const passwordConfirm = fg.controls['confirmPassword'].value;
  if(password === '')
    return {passwordMatch: true} ;
  return password !== passwordConfirm ? {passwordMatch: true} : null;
};
