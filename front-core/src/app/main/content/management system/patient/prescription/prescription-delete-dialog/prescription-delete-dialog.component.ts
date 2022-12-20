import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ContactService } from '../../../contact/contact.service';

@Component({
  selector: 'app-prescription-delete-dialog',
  templateUrl: './prescription-delete-dialog.component.html',
  styleUrls: ['./prescription-delete-dialog.component.sass']
})
export class PrescriptionDeleteDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<PrescriptionDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public contactService: ContactService
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
  confirmDelete(): void {
    this.dialogRef.close(this.data.id);
  }

}
