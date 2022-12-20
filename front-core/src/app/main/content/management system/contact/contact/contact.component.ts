import { SelectionModel } from '@angular/cdk/collections';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UtilService } from 'src/app/main/util.service';
import { Patient } from '../../patient/patient.model';
import { ContactDeleteDialogComponent } from '../contact-delete-dialog/contact-delete-dialog.component';
import { ContactFormDialogComponent } from '../contact-form-dialog/contact-form-dialog.component';
import { Contact } from '../contact.model';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.sass']
})
export class ContactComponent implements OnInit {
  selection = new SelectionModel<Contact>(true, []);
  dataSource: MatTableDataSource<Contact>;
  contacts: Contact[];
  elements: any[];
  @Input() patient: Patient;

  displayedColumns = [
    "firstName",
    "lastName",
    "contactType",
    "phone",
    "email",
    "actions"
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private contactService: ContactService,
              public dialog: MatDialog,
              private utilService: UtilService) { }

  ngOnInit(): void {
    this.getData();
  }

  private getData() {
    if(this.patient) {
      this.contactService.getAllContactsByPatientId(this.patient.id).subscribe((response: any) => {
        this.contacts = response;
        this.createTableData();
      });
    }else {
      this.contactService.getAll().subscribe((response: any) => {
        this.contacts = response;
        this.createTableData();
      });
    }  
  }

  createTableData() {
    this.elements = [];
    for (let i = 0; i < this.contacts.length; i++) {
      this.elements.push({
        id: this.contacts[i].id,
        firstName: this.contacts[i].firstName,
        lastName: this.contacts[i].lastName,
        contactType: this.contacts[i].contactType,
        contactTypeOther: this.contacts[i].contactTypeOther,
        city: this.contacts[i].city,
        houseNumber: this.contacts[i].houseNumber,
        street: this.contacts[i].street,
        zip: this.contacts[i].zip,
        phone: this.contacts[i].phone,
        email: this.contacts[i].email,
        note: this.contacts[i].note,
      });
    }
    this.dataSource = new MatTableDataSource<Contact>(this.elements);
  }

  addNew(contact?: Contact) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(ContactFormDialogComponent, {
      data: {
        contact: contact,
        action: "add",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((contact: Contact) => {
      if (contact) {
        if(this.patient) {
          this.contactService.saveContactToPatient(this.patient.id ,contact).subscribe({
            next: () => {
              this.getData();
              this.utilService.showNotification(
                "snackbar-success",
                "Add Contact Successfully...!!!",
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
        }else {
          this.contactService.save(contact).subscribe({
            next: () => {
              this.getData();
              this.utilService.showNotification(
                "snackbar-success",
                "Add Contact Successfully...!!!",
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
    const dialogRef = this.dialog.open(ContactFormDialogComponent, {
      data: {
        contact: row,
        action: "edit",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((contact: Contact) => {
      if (contact) {
        this.contactService.update(contact.id, contact).subscribe({
          next: () => {
            this.getData();
            this.utilService.showNotification(
              "black",
              `Edit Contact Successfully...!!!`,
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
    const dialogRef = this.dialog.open(ContactFormDialogComponent, {
      data: {
        contact: row,
        action: "details",
      },
      height: "50%",
      width: "40%",
      direction: tempDirection,
    });
  }

  deleteItem(row) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(ContactDeleteDialogComponent, {
      data: row,
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((id: number) => {
      if (id) {
        this.contactService.delete(id).subscribe({
          next: () => {
            this.getData();
            this.utilService.showNotification(
              "snackbar-danger",
              "Delete Contact Successfully...!!!",
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
  
}
