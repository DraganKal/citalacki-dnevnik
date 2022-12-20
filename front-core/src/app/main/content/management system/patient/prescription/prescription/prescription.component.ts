import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { UtilService } from 'src/app/main/util.service';
import { PrescriptionDeleteDialogComponent } from '../prescription-delete-dialog/prescription-delete-dialog.component';
import { PrescriptionFormDialogComponent } from '../prescription-form-dialog/prescription-form-dialog.component';
import { Prescription } from '../prescription.model';
import { PrescriptionService } from '../prescription.service';

@Component({
  selector: 'app-prescription',
  templateUrl: './prescription.component.html',
  styleUrls: ['./prescription.component.sass']
})
export class PrescriptionComponent implements OnInit {
  dataSource: MatTableDataSource<Prescription>;
  prescriptions: Prescription[];
  elements: any[];
  @Input() patientId: number;

  displayedColumns = [
    "name",
    "issuingDoctor",
    "healthInsuranceStatus",
    "healthInsuranceNumber",
    "numberOfRequiredAppointments",
    "actions"
  ];

  constructor(private prescriptionService: PrescriptionService,
              private utilService: UtilService,
              public dialog: MatDialog,) { }

  ngOnInit(): void {
    this.getData();
  }

  private getData() {
    this.prescriptionService.getAllByPatientId(this.patientId).subscribe((response: any) => {
      this.prescriptions = response;
        this.createTableData();
    });
  }

  createTableData() {
    this.elements = [];
    for (let i = 0; i < this.prescriptions.length; i++) {
      this.elements.push({
        id: this.prescriptions[i].id,
        issuingDoctor: this.prescriptions[i].issuingDoctor,
        name: this.prescriptions[i].name,
        city: this.prescriptions[i].city,
        houseNumber: this.prescriptions[i].houseNumber,
        street: this.prescriptions[i].street,
        zip: this.prescriptions[i].zip,
        healthInsuranceStatus: this.prescriptions[i].healthInsuranceStatus,
        healthInsuranceNumber: this.prescriptions[i].healthInsuranceNumber,
        history: this.prescriptions[i].history,
        numberOfRequiredAppointments: this.prescriptions[i].numberOfRequiredAppointments,
        patientId: this.prescriptions[i].patientId,
        patientFirstName: this.prescriptions[i].patientFirstName,
        patientLastName: this.prescriptions[i].patientLastName,
      });
    }
    this.dataSource = new MatTableDataSource<Prescription>(this.elements);
  }

  addNew(prescription?: Prescription) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(PrescriptionFormDialogComponent, {
      data: {
        prescription: prescription,
        action: "add",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((prescription: Prescription) => {
      if (prescription) {
        if(this.patientId) {
          this.prescriptionService.save(this.patientId ,prescription).subscribe({
            next: () => {
              this.getData();
              this.utilService.showNotification(
                "snackbar-success",
                "Add Prescription Successfully...!!!",
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
    const dialogRef = this.dialog.open(PrescriptionFormDialogComponent, {
      data: {
        prescription: row,
        action: "edit",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((prescription: Prescription) => {
      if (prescription) {
        this.prescriptionService.update(this.patientId, prescription.id, prescription).subscribe({
          next: () => {
            this.getData();
            this.utilService.showNotification(
              "black",
              `Edit Prescription Successfully...!!!`,
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

  details(row) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(PrescriptionFormDialogComponent, {
      data: {
        prescription: row,
        action: "details",
      },
      height: "50%",
      width: "40%",
      direction: tempDirection,
    });
  }

  refresh() {
    this.getData();
  }

  deleteItem(row) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(PrescriptionDeleteDialogComponent, {
      data: row,
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((id: number) => {
      if (id) {
        this.prescriptionService.delete(this.patientId, id).subscribe({
          next: () => {
            this.getData();
            this.utilService.showNotification(
              "snackbar-danger",
              "Delete Prescription Successfully...!!!",
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

}
