import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Employee } from '../employee.model';
import { EmployeeService } from '../employee.service';
import { NewEmployeeDialogComponent } from '../new-employee-dialog/new-employee-dialog.component';
import { MatSnackBar } from "@angular/material/snack-bar";
import { TranslateService } from '@ngx-translate/core';
import {locale as german} from '../i18n/de';
import {locale as english} from '../i18n/en';
import { FuseTranslationLoaderService } from 'src/app/core/service/translation-loader.service';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/core/service/user.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.sass']
})
export class EmployeeComponent implements OnInit {

  employees: Employee[] = new Array;
  selection = new SelectionModel<Employee>(true, []);
  employee: Employee | null;
  employeeId: number;
  dataSource;
  resultsLength: number;
  private paginatorSet: boolean;

  displayedColumns = [
    "select",
    "firstName",
    "lastName",
    "username",
    "email",
    "date",
    "actions"
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(private employeeService: EmployeeService,
              public dialog: MatDialog,
              private snackBar: MatSnackBar,
              private translate: TranslateService,
              private fuseTranslationLoader: FuseTranslationLoaderService,
              private userService: UserService) {
      this.fuseTranslationLoader.loadTranslations(english, german);
               }

  ngOnInit(): void {
    this.getAllEmployees();
  }

  loadData() {
    this.getAllEmployees();
  }

  getAllEmployees() {
    this.employeeService.getAllEmployees().subscribe(
     ( res:[]) => {
        this.employees = res;
        this.resultsLength = +res.length;
        this.createTableData();
      }
     )
  }

  createTableData() {
    this.dataSource = new MatTableDataSource<Employee>(this.employees);
    // if (!this.paginatorSet) {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.paginatorSet = true;
    // }
  }

  addNew() {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(NewEmployeeDialogComponent, {
      data: {
        employee: this.employee,
        action: "create",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.getAllEmployees();
        this.showNotification(
          "snackbar-success",
          "Add Employee Successfully!",
          "bottom",
          "center"
        );
      }
    });
  }

  deleteEmployee(row) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: {
        employee: row,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if(result == 1) {
       this.getAllEmployees();
      }
    })
  }

  editEmployee(row) {
    this.employeeId = row.id;
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(NewEmployeeDialogComponent, {
      data: {
        employee: row,
        action: "edit",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.showNotification(
          "snackbar-success",
          "Employee edited Successfully!",
          "bottom",
          "center"
        );
      }
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

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.employees.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.employees.forEach((row) =>
          this.selection.select(row)
        );
  }

  sendAccessData(){
    let selectedIds = [];
    this.selection.selected.forEach((employee) => 
      selectedIds.push(employee.id)
    );

    this.userService.sendPasswordsToUsers(selectedIds).subscribe(data =>{
      this.showNotification(
        "snackbar-success",
        "Password reset sent to mail Successfully!",
        "bottom",
        "center"
      );
      this.loadData();
      this.selection = new SelectionModel<Employee>(true, []);
     
    });
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
