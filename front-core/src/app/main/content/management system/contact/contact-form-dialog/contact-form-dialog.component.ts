import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Contact } from '../contact.model';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contact-form-dialog',
  templateUrl: './contact-form-dialog.component.html',
  styleUrls: ['./contact-form-dialog.component.sass']
})
export class ContactFormDialogComponent{
  action: string;
  dialogTitle: string;
  contactForm: FormGroup;
  contact: Contact;
  isContactTypeOther: boolean = false;
  isDetails: boolean;
  constructor(
    public dialogRef: MatDialogRef<ContactFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public contactService: ContactService,
    private fb: FormBuilder
  ) {
    this.action = data.action;
    if (this.action === "edit") {
      this.dialogTitle = data.contact.firstName + " " +  data.contact.lastName;
      this.contact = data.contact;
      this.isDetails = false;
    } else if(this.action === "add"){
      this.dialogTitle = "New Contact";
      this.contact = new Contact({});
      this.isDetails = false;
    }else if(this.action === "details") {
      this.dialogTitle = data.contact.firstName + " " +  data.contact.lastName;
      this.contact = data.contact;
      this.isDetails = true;
    }
    this.isContactTypeOther = this.contact.contactType === 'OTHER'? true: false;
    this.contactForm = this.createContactForm();
    if(!this.isContactTypeOther) {
      this.contactForm.controls['contactTypeOther'].clearValidators();
      this.contactForm.controls['contactTypeOther'].updateValueAndValidity();
    } 
  }
  formControl = new FormControl("", [
    Validators.required,
  ]);
  createContactForm(): FormGroup {
    return this.fb.group({
      id: [this.contact.id],
      firstName: [this.contact.firstName, [Validators.required]],
      lastName: [this.contact.lastName, [Validators.required]],
      contactType: [this.contact.contactType, [Validators.required]],
      contactTypeOther: [this.contact.contactTypeOther, [Validators.required]],
      phone: [this.contact.phone],
      city: [this.contact.city],
      houseNumber: [this.contact.houseNumber],
      street: [this.contact.street],
      zip: [this.contact.zip],
      email: [
        this.contact.email,
        [Validators.email, Validators.minLength(5)],
      ],
      note: [this.contact.note],
    });
  }

  contactTypeChanged(event) {
    this.isContactTypeOther = event.value === 'OTHER'? true: false;
    if(!this.isContactTypeOther) {
      this.contactForm.controls['contactTypeOther'].clearValidators();
      this.contactForm.controls['contactTypeOther'].updateValueAndValidity();
      this.contactForm.controls['contactTypeOther'].markAsUntouched();
      this.contactForm.controls['contactTypeOther'].setValue(null);
    }
    
  }
}
