import { SelectionModel } from '@angular/cdk/collections';
import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Role } from 'src/app/core/models/role';
import { AuthenticationService } from 'src/app/core/service/authentication.service';
import { UtilService } from 'src/app/main/util.service';
import { PracticeManagementDeleteDialogComponent } from '../practice-management-delete-dialog/practice-management-delete-dialog.component';
import { PracticeManagementFormDialogComponent } from '../practice-management-form-dialog/practice-management-form-dialog.component';
import { PracticeManagementService } from '../practice-management.service';
import { Practice } from '../practice.model';

@Component({
  selector: 'app-practice-management',
  templateUrl: './practice-management.component.html',
  styleUrls: ['./practice-management.component.sass']
})
export class PracticeManagementComponent implements OnInit {
  selection = new SelectionModel<any>(true, []);
  dataSource: MatTableDataSource<Practice>;
  practices: Practice[];
  elements;
  logo;

  displayedColumns = [
    "select",
    "name",
    "actions",
  ];

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private practiceManagementService: PracticeManagementService,
              private sanitizer: DomSanitizer,
              private dialog: MatDialog,
              private utilService: UtilService) { }

  ngOnInit(): void {
    const user = this.authenticationService.getUser();
    const userRole = user.userType;
    if(userRole === Role.ADMINISTRATOR) {
      this.router.navigateByUrl('/practice-management/' + user.practiceId);
    }else if(userRole === Role.SUPER_ADMINISTRATOR) {
      this.practiceManagementService.getAll().subscribe(response => {
        this.getData();
      });
    }
  }
  private getData() {
    this.practiceManagementService.getAll().subscribe((response: any) => {
      this.practices = response;
      // this.elements = this.practices.map((element) => {
      //   var binaryData = [];
      //   binaryData.push(element.logo);
      //   return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(new Blob(binaryData)));
      // });
      // this.patients = response.body.content;
      // this.resultsLength = +response.headers.get('X-total-elements');
      this.createTableData();
    });
  }

  createTableData() {
    this.elements = [];
    for (let i = 0; i < this.practices.length; i++) {
      // var binaryData = [];
      // binaryData.push(this.practices[i].logo);
      // this.logo = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(new Blob([this.practices[i].logo])));
      this.elements.push({
        id: this.practices[i].id,
        name: this.practices[i].name,
        // logo: this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(new Blob([this.practices[i].logo])))
      });
    }
    this.dataSource = new MatTableDataSource<Practice>(this.elements);
  }

  removeSelectedRows() {

  }

  addNew(practice?: Practice) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(PracticeManagementFormDialogComponent, {
      data: {
        practice: practice,
        action: "add",
      },
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((practice: Practice) => {
      if (practice) {
        this.practiceManagementService.add(practice).subscribe({
          next: () => {
            this.getData();
            this.utilService.showNotification(
              "snackbar-success",
              "Add Practice Successfully...!!!",
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

  edit(id: number) {
    this.router.navigateByUrl('/practice-management/' + id)
    // let tempDirection;
    // if (localStorage.getItem("isRtl") === "true") {
    //   tempDirection = "rtl";
    // } else {
    //   tempDirection = "ltr";
    // }
    // const dialogRef = this.dialog.open(PatientFormDialogComponent, {
    //   data: {
    //     patient: row,
    //     action: "edit",
    //   },
    //   direction: tempDirection,
    // });
    // dialogRef.afterClosed().subscribe((patient: Patient) => {
    //   if (patient) {
    //     this.patientService.putPatient(patient.id, patient).subscribe({
    //       next: () => {
    //         this.getData();
    //         this.utilService.showNotification(
    //           "black",
    //           `Edit Patient Successfully...!!!`,
    //           "bottom",
    //           "center"
    //         );
    //       },
    //       error: (error) =>{
    //         this.utilService.showNotification(
    //           "snackbar-danger",
    //           error.error.message,
    //           "bottom",
    //           "center"
    //         );
    //       }
    //     });
    //   }
    // });
  }

  deleteItem(row) {
    let tempDirection;
    if (localStorage.getItem("isRtl") === "true") {
      tempDirection = "rtl";
    } else {
      tempDirection = "ltr";
    }
    const dialogRef = this.dialog.open(PracticeManagementDeleteDialogComponent, {
      data: row,
      direction: tempDirection,
    });
    dialogRef.afterClosed().subscribe((id: number) => {
      if (id) {
        this.practiceManagementService.delete(id).subscribe({
          next: () => {
            this.getData();
            this.utilService.showNotification(
              "snackbar-danger",
              "Delete Practice Successfully...!!!",
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
  
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }
  
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach((row) =>
          this.selection.select(row)
        );
  }
}
