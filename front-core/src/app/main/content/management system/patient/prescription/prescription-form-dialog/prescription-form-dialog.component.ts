import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Prescription } from '../prescription.model';
import { PrescriptionService } from '../prescription.service';

@Component({
  selector: 'app-prescription-form-dialog',
  templateUrl: './prescription-form-dialog.component.html',
  styleUrls: ['./prescription-form-dialog.component.sass']
})
export class PrescriptionFormDialogComponent {
  action: string;
  dialogTitle: string;
  prescriptionForm: FormGroup;
  prescription: Prescription;
  isDetails: boolean;
  constructor(public dialogRef: MatDialogRef<PrescriptionFormDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public prescriptionService: PrescriptionService,
              private fb: FormBuilder) {
  this.action = data.action;
    if (this.action === "edit") {
      this.dialogTitle = data.prescription.name;
      this.prescription = data.prescription;
      this.isDetails = false;
    } else if(this.action === "add"){
      this.dialogTitle = "New Prescription";
      this.prescription = new Prescription({});
      this.isDetails = false;
    }else if(this.action === "details") {
      this.dialogTitle = data.prescription.name;
      this.prescription = data.prescription;
      this.isDetails = true;
    }
    this.prescriptionForm = this.createPrescriptionForm();
  }

  createPrescriptionForm(): FormGroup {
    return this.fb.group({
      id: [this.prescription.id],
      issuingDoctor: [this.prescription.issuingDoctor],
      name: [this.prescription.name],
      city: [this.prescription.city],
      houseNumber: [this.prescription.houseNumber],
      street: [this.prescription.street],
      zip: [this.prescription.zip],
      healthInsuranceStatus: [this.prescription.healthInsuranceStatus],
      healthInsuranceNumber: [this.prescription.healthInsuranceNumber],
      history: [this.prescription.history],
      numberOfRequiredAppointments: [this.prescription.numberOfRequiredAppointments],
      patientId: [this.prescription.patientId],
      patientFirstName: [this.prescription.patientFirstName],
      patientLastName: [this.prescription.patientLastName], 
    });
  }

}
