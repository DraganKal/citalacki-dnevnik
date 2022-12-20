import { SelectionModel } from '@angular/cdk/collections';
import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { TranslatePipe } from '@ngx-translate/core';
import { merge } from 'rxjs';
import { UtilService } from 'src/app/main/util.service';
import { UnsubscribeOnDestroyAdapter } from 'src/app/shared/UnsubscribeOnDestroyAdapter';
import { PatientDeleteDialogComponent } from '../patient-delete-dialog/patient-delete-dialog.component';
import { PatientFormDialogComponent } from '../patient-form-dialog/patient-form-dialog.component';
import { Patient } from '../patient.model';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.sass']
})
export class PatientComponent  implements OnInit {

  selection = new SelectionModel<any>(true, []);
  dataSource: MatTableDataSource<any>;
  patients: Patient[];
  elements;

  resultsLength = 0;
  pageSize = 10;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild("filter", { static: true }) filter: ElementRef;

  displayedColumns = [
    "select",
    "fixedPatientNumber",
    "firstName",
    "lastName",
    "phone",
    "actions",
  ];

  constructor(public patientService: PatientService,
              public dialog: MatDialog,
              private utilService: UtilService) {}

  ngOnInit(): void {
   this.getData();
   merge(this.paginator.page).subscribe(
    (data) => {
      this.getData();
    }
  );
  }

  private getData() {
    this.patientService.getAll(
      this.paginator.pageIndex,
      this.pageSize,
      "id",
      "asc").subscribe((response: HttpResponse<any>) => {
      this.patients = response.body.content;
      this.resultsLength = +response.headers.get('X-total-elements');
      this.createTableData();
    });
  }

  createTableData() {
    this.elements = [];
    for (let i = 0; i < this.patients.length; i++) {
      this.elements.push({
        id: this.patients[i].id,
        fixedPatientNumber: this.patients[i].fixedPatientNumber,
        code: this.patients[i].code,
        firstName: this.patients[i].firstName,
        lastName: this.patients[i].lastName,
        phone: this.patients[i].phone,
        city: this.patients[i].city,
        houseNumber: this.patients[i].houseNumber,
        street: this.patients[i].street,
        zip: this.patients[i].zip,
        email: this.patients[i].email,
      });
    }

    this.dataSource = new MatTableDataSource<Patient>(this.elements);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
}

removeSelectedRows(){

}

addNew(patient?: Patient) {
  let tempDirection;
  if (localStorage.getItem("isRtl") === "true") {
    tempDirection = "rtl";
  } else {
    tempDirection = "ltr";
  }
  const dialogRef = this.dialog.open(PatientFormDialogComponent, {
    data: {
      patient: patient,
      action: "add",
    },
    direction: tempDirection,
  });
  dialogRef.afterClosed().subscribe((patient: Patient) => {
    if (patient) {
      this.patientService.addPatient(patient).subscribe({
        next: () => {
          this.getData();
          this.utilService.showNotification(
            "snackbar-success",
            "Add Patient Successfully...!!!",
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
  });
}

edit(row) {
  let tempDirection;
  if (localStorage.getItem("isRtl") === "true") {
    tempDirection = "rtl";
  } else {
    tempDirection = "ltr";
  }
  const dialogRef = this.dialog.open(PatientFormDialogComponent, {
    data: {
      patient: row,
      action: "edit",
    },
    direction: tempDirection,
  });
  dialogRef.afterClosed().subscribe((patient: Patient) => {
    if (patient) {
      this.patientService.putPatient(patient.id, patient).subscribe({
        next: () => {
          this.getData();
          this.utilService.showNotification(
            "black",
            `Edit Patient Successfully...!!!`,
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
  });
}

deleteItem(row) {
  let tempDirection;
  if (localStorage.getItem("isRtl") === "true") {
    tempDirection = "rtl";
  } else {
    tempDirection = "ltr";
  }
  const dialogRef = this.dialog.open(PatientDeleteDialogComponent, {
    data: row,
    direction: tempDirection,
  });
  dialogRef.afterClosed().subscribe((id: number) => {
    if (id) {
      this.patientService.deletePatient(id).subscribe({
        next: () => {
          this.getData();
          this.utilService.showNotification(
            "snackbar-danger",
            "Delete Patient Successfully...!!!",
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
  });
}

refresh(){
  this.getData();
}

isAllSelected() {
  const numSelected = this.selection.selected.length;
  const numRows = this.dataSource.data.length;
  return numSelected === numRows;
}

masterToggle() {
  this.isAllSelected()
    ? this.selection.clear()
    : this.dataSource.data.forEach((row) =>
        this.selection.select(row)
      );
}
// showNotification(colorName, text, placementFrom, placementAlign) {
//   this.snackBar.open(this.translate.transform(text), "", {
//     duration: 2000,
//     verticalPosition: placementFrom,
//     horizontalPosition: placementAlign,
//     panelClass: colorName,
//   });
// }

}
