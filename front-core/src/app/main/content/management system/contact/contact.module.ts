import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactComponent } from './contact/contact.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactRoutingModule } from './contact-routing.module';
import { ContactDeleteDialogComponent } from './contact-delete-dialog/contact-delete-dialog.component';
import { ContactFormDialogComponent } from './contact-form-dialog/contact-form-dialog.component';
import { ContactDashboardComponent } from './contact-dashboard/contact-dashboard.component';
import { ComponentsModule } from 'src/app/shared/components/components.module';



@NgModule({
  declarations: [
    ContactComponent,
    ContactDeleteDialogComponent,
    ContactFormDialogComponent,
    ContactDashboardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ComponentsModule,
    SharedModule,
    ContactRoutingModule,
    
  ],
  exports: [
    ContactComponent,
    ContactDeleteDialogComponent,
    ContactFormDialogComponent
  ]
})
export class ContactModule { }
