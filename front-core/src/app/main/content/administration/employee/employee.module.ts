import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { EmployeeComponent } from './employee/employee.component';
import { EmployeeRoutingModule } from './employee-routing.module';
import {MatTableModule} from '@angular/material/table'
import { MatIconModule } from '@angular/material/icon';
import { SharedModule } from 'src/app/shared/shared.module';
import { NewEmployeeDialogComponent } from './new-employee-dialog/new-employee-dialog.component';
import { DeleteDialogComponent } from './delete-dialog/delete-dialog.component';
import { ComponentsModule } from 'src/app/shared/components/components.module';



@NgModule({
  declarations: [
    EmployeeComponent,
    NewEmployeeDialogComponent,
    DeleteDialogComponent
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    SharedModule,
    ComponentsModule
  ]
})
export class EmployeeModule { }
