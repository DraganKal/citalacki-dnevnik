import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contact-delete-dialog',
  templateUrl: './contact-delete-dialog.component.html',
  styleUrls: ['./contact-delete-dialog.component.sass']
})
export class ContactDeleteDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<ContactDeleteDialogComponent>,
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
