import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Employee } from '../employee.model';
import { EmployeeService } from '../employee.service';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-new-employee-dialog',
  templateUrl: './new-employee-dialog.component.html',
  styleUrls: ['./new-employee-dialog.component.sass']
})
export class NewEmployeeDialogComponent implements OnInit {

  action: string;
  employee: Employee;

  employeeForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<NewEmployeeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public employeeService: EmployeeService,
              private formBuilder: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar,
              private translate: TranslateService) {
    this.action = data.action;
    if (this.action === "edit") {
      this.employee = data.employee;
    } else {
      this.employee = new Employee({});
    }
  }

  ngOnInit(): void {
    this.createEmployeeForm();
  }

  createEmployeeForm() {
    if(this.action === 'edit'){
      this.employeeForm = this.formBuilder.group({
        id:[this.employee.id],
        firstName: [this.employee.firstName],
        lastName: [this.employee.lastName],
        username: [this.employee.username, Validators.required],
        email: [this.employee.email, Validators.required],
        phone: [this.employee.phone],
        userType: [this.employee.userType, Validators.required],
        city: [this.employee.city],
        street: [this.employee.street],
        houseNumber: [this.employee.houseNumber],
        zip:[this.employee.zip],
      });
    } else {
      this.employeeForm = this.formBuilder.group({
        id:[],
        firstName: [''],
        lastName: [''],
        username: ['', Validators.required],
        email: ['', Validators.required],
        phone: [],
        userType: ['EMPLOYEE', Validators.required],
        city: [''],
        street: [''],
        houseNumber: [''],
        zip:[],
      });
    }
  }

  submit() {
    this.employee.id = this.employeeForm.controls['id'].value;
    this.employee.firstName = this.employeeForm.controls['firstName'].value;
    this.employee.lastName = this.employeeForm.controls['lastName'].value;
    this.employee.username = this.employeeForm.controls['username'].value;
    this.employee.email = this.employeeForm.controls['email'].value;
    this.employee.phone = this.employeeForm.controls['phone'].value;
    this.employee.userType = this.employeeForm.controls['userType'].value;
    this.employee.city = this.employeeForm.controls['city'].value;
    this.employee.street = this.employeeForm.controls['street'].value;
    this.employee.houseNumber = this.employeeForm.controls['houseNumber'].value;
    this.employee.zip = this.employeeForm.controls['zip'].value;

    if(this.action === 'edit') {
      this.employeeService.updateEmployee(this.employee.id, this.employee).subscribe((res:any)=>{
        this.dialogRef.close(1);   
      }, (error) => {
        this.showNotification(
          "snackbar-error",
          this.getTranslation(error.error.message),
          "bottom",
          "center"
        );
      })
    } else {
      this.employeeService.saveEmployee(this.employee).subscribe((res:any)=>{
        this.dialogRef.close(1);   
      }, (error) => {
        this.showNotification(
          "snackbar-error",
          this.getTranslation(error.error.message),
          "bottom",
          "center"
        );
      })
    }
      
    
  }


  close(): void {
    this.dialogRef.close();
  }

  showNotification(colorName, text, placementFrom, placementAlign) {
    this.snackBar.open(text, "", {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }

  getTranslation(str) {
    const currentLang = this.translate.currentLang; // get current language
    const returnValue = this.translate.translations[currentLang][str]; // get converted string from current language
    if (returnValue === undefined) {
      return this.translate.translations.en_merch[str]; // if value is getting undefined then return default language string, en_merch is default english language file here
    } else {
      return returnValue;
    }
  }


}
