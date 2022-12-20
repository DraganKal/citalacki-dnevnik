import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Patient } from '../patient.model';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-form-dialog',
  templateUrl: './patient-form-dialog.component.html',
  styleUrls: ['./patient-form-dialog.component.sass']
})
export class PatientFormDialogComponent{
  action: string;
  dialogTitle: string;
  patientForm: FormGroup;
  patient;
  constructor(
    public dialogRef: MatDialogRef<PatientFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public patientService: PatientService,
    private fb: FormBuilder
  ) {
    this.action = data.action;
    if (this.action === "edit") {
      this.dialogTitle = data.patient.firstName + " " + data.patient.lastName;
      this.patient = data.patient;
    } else {
      this.dialogTitle = "New Patient";
      this.patient = new Patient({});
    }
    this.patientForm = this.createContactForm();
  }
  formControl = new FormControl("", [
    Validators.required,
    Validators.email,
  ]);
  createContactForm(): FormGroup {
    return this.fb.group({
      id: [this.patient.id],
      fixedPatientNumber: [this.patient.fixedPatientNumber, [Validators.required]],
      code: [this.patient.code, [Validators.required]],
      firstName: [this.patient.firstName, [Validators.required]],
      lastName: [this.patient.lastName, [Validators.required]],
      phone: [this.patient.phone],
      city: [this.patient.city],
      houseNumber: [this.patient.houseNumber],
      street: [this.patient.street],
      zip: [this.patient.zip],
      email: [
        this.patient.email,
        [Validators.email, Validators.minLength(5)],
      ],
    });
  }
}
