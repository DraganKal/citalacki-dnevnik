import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-delete-dialog',
  templateUrl: './patient-delete-dialog.component.html',
  styleUrls: ['./patient-delete-dialog.component.sass']
})
export class PatientDeleteDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<PatientDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public patientService: PatientService
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
  confirmDelete(): void {
    this.dialogRef.close(this.data.id);
  }

}
