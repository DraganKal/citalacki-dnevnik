import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PracticeManagementService } from '../practice-management.service';

@Component({
  selector: 'app-practice-management-delete-dialog',
  templateUrl: './practice-management-delete-dialog.component.html',
  styleUrls: ['./practice-management-delete-dialog.component.sass']
})
export class PracticeManagementDeleteDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<PracticeManagementDeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public practicemanagementService: PracticeManagementService
  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
  confirmDelete(): void {
    this.dialogRef.close(this.data.id);
  }

}
