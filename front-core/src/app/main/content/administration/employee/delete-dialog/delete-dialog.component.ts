import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { FuseTranslationLoaderService } from 'src/app/core/service/translation-loader.service';
import { Employee } from '../employee.model';
import { EmployeeService } from '../employee.service';
import {locale as german} from '../i18n/de';
import {locale as english} from '../i18n/en';

@Component({
  selector: 'app-delete-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.sass']
})
export class DeleteDialogComponent {

  employee: Employee;

  constructor(
    public dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public employeeService: EmployeeService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private fuseTranslationLoader: FuseTranslationLoaderService,
  ) {
    this.fuseTranslationLoader.loadTranslations(english, german);
    this.employee = data.employee;
    
  }
  close(): void {
    this.dialogRef.close();
  }
  confirmDelete(): void {
    this.employeeService.deleteEmployee(this.employee.id).subscribe(
      res => {
        this.showNotification(
          "snackbar-success",
          "Employee deleted Successfully!",
          "bottom",
          "center"
        );
        this.dialogRef.close(1)
      }, (error) => {
        this.showNotification(
          "snackbar-error",
          this.getTranslation(error.error.message),
          "bottom",
          "center"
        );
      }
    )
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
