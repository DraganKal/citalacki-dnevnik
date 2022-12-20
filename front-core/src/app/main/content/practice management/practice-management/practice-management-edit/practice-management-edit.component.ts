import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/core/service/authentication.service';
import { UserService } from 'src/app/core/service/user.service';
import { LogopedicPractice } from '../logopedic-practice.model';
import { PracticeManagementService } from '../practice-management.service';
// import { Practice } from '../practice.model';

@Component({
  selector: 'app-practice-management',
  templateUrl: './practice-management-edit.component.html',
  styleUrls: ['./practice-management-edit.component.scss']
})
export class PracticeManagementEditComponent implements OnInit {
  practiceId: number;
  form: FormGroup;
  practice: LogopedicPractice;
  logo;
  validSize = true;
  maxSize=1048576*20;
  fileInput;
  colorHex1 = '';
  colorHex2 = '';
  constructor(private practiceManagementService: PracticeManagementService,
              private fb: FormBuilder,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer,
              private userService: UserService,
              private authenticationService: AuthenticationService,
              private router: Router,) 
  {
    this.form = this.fb.group({
      name: [""],
      logoUrl: [""],
      colorHex1: [""],
      colorHex2: [""],
    });
  }

  ngOnInit(): void {
    this.practiceId = this.route.snapshot.params.id;
    this.practiceManagementService.getOne(this.practiceId).subscribe((response: any) => {
      this.practice = response;
      this.colorHex1 = this.practice.colorHex1;
      this.colorHex2 = this.practice.colorHex2;
      this.setValues();
    });
    this.practiceManagementService.getLogo(this.practiceId).subscribe((response: any) => {
      if(response.size !== 0) {
        this.logo = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(response));
      }
    });
  }

  setValues() {
    this.form.controls['name'].setValue(this.practice.name);
    this.form.controls['logoUrl'].setValue('');
    this.form.controls['colorHex1'].setValue(this.colorHex1);
    this.form.controls['colorHex2'].setValue(this.colorHex2);
  }

  save() {
    let practice: LogopedicPractice = new LogopedicPractice({
      id: this.practiceId,
      name: this.form.controls['name'].value,
      colorHex1: this.colorHex1,
      colorHex2: this.colorHex2,
    })
    this.practiceManagementService.put(this.practiceId, practice).subscribe((response: any) => {
      const user = this.authenticationService.getUser();
      this.userService.getUserById(user.id).subscribe((user) => {
        localStorage.setItem('user', JSON.stringify(user));
        if(this.fileInput) {
          const fd = new FormData();
          fd.append('image', this.fileInput);
          this.userService.uploadImage(this.practiceId, fd).subscribe(response => {
            this.reloadCurrentRoute();
          });
        }else {
          this.reloadCurrentRoute();
        }
      });

    });
  }


  imageUpload(event) {
    if (event.target.files[0].size <= this.maxSize) {
      const urlCreator = window.URL;
      this.logo = this.sanitizer.bypassSecurityTrustUrl(
        urlCreator.createObjectURL(event.target.files[0]));
        this.fileInput = event.target.files[0];
    } 
  }

  reloadCurrentRoute() {
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
  }

  colorChange() {
    console.log("color change method")
  }

}
