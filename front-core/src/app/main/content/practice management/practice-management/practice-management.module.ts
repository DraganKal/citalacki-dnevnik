import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PracticeManagementRoutingModule } from './practice-management-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ComponentsModule } from 'src/app/shared/components/components.module';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { PracticeManagementService } from './practice-management.service';
import { PracticeManagementComponent } from './practice-management/practice-management.component';
import { PracticeManagementEditComponent } from './practice-management-edit/practice-management-edit.component';
import { PracticeManagementFormDialogComponent } from './practice-management-form-dialog/practice-management-form-dialog.component';
import { PracticeManagementDeleteDialogComponent } from './practice-management-delete-dialog/practice-management-delete-dialog.component'
import { ColorPickerModule } from "ngx-color-picker";

@NgModule({
  declarations: [
    PracticeManagementComponent,
    PracticeManagementEditComponent,
    PracticeManagementFormDialogComponent,
    PracticeManagementDeleteDialogComponent
  ],
  imports: [
    CommonModule,
    PracticeManagementRoutingModule,
    SharedModule,
    ComponentsModule,
    MaterialFileInputModule,
    ColorPickerModule,
    
  ],
  providers: [
    PracticeManagementService
  ],
})
export class PracticeManagementModule { }
