import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PracticeManagementService } from '../practice-management.service';
import { Practice } from '../practice.model';

@Component({
  selector: 'app-practice-management-form-dialog',
  templateUrl: './practice-management-form-dialog.component.html',
  styleUrls: ['./practice-management-form-dialog.component.sass']
})
export class PracticeManagementFormDialogComponent {

  action: string;
  dialogTitle: string;
  practiceForm: FormGroup;
  practice;
  constructor(
    public dialogRef: MatDialogRef<PracticeManagementFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public practiceManagementService: PracticeManagementService,
    private fb: FormBuilder
  ) {
    this.action = data.action;
    if (this.action === "edit") {
      this.dialogTitle = data.practice.name;
      this.practice = data.practice;
    } else {
      this.dialogTitle = "New Practice";
      this.practice = new Practice({});
    }
    this.practiceForm = this.createContactForm();
  }
  formControl = new FormControl("", [
    Validators.required,
  ]);
  createContactForm(): FormGroup {
    return this.fb.group({
      id: [this.practice.id],
      name: [this.practice.name, [Validators.required]]
    });
  }

}
