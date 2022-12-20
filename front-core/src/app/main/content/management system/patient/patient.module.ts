import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient/patient.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PatientRoutingModule } from './patient-routing.module';
import { PatientService } from './patient.service';
import { ComponentsModule } from 'src/app/shared/components/components.module';
import { PatientFormDialogComponent } from './patient-form-dialog/patient-form-dialog.component';
import { PatientDeleteDialogComponent } from './patient-delete-dialog/patient-delete-dialog.component';
import { TranslatePipe } from '@ngx-translate/core';
import { PatientDetailsComponent } from './patient-details/patient-details.component';
import { ContactModule } from '../contact/contact.module';
import { PrescriptionComponent } from './prescription/prescription/prescription.component';
import { PrescriptionFormDialogComponent } from './prescription/prescription-form-dialog/prescription-form-dialog.component';
import { PrescriptionDeleteDialogComponent } from './prescription/prescription-delete-dialog/prescription-delete-dialog.component';

@NgModule({
  declarations: [
    PatientComponent,
    PatientFormDialogComponent,
    PatientDeleteDialogComponent,
    PatientDetailsComponent,
    PrescriptionComponent,
    PrescriptionFormDialogComponent,
    PrescriptionDeleteDialogComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    PatientRoutingModule,
    ComponentsModule,
    ContactModule
    
  ],
  providers: [
    PatientService, TranslatePipe
  ],
})
export class PatientModule { }
