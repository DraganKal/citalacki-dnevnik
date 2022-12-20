import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationComponent } from './location/location.component';
import { AddLocationComponent } from './add-location/add-location.component';
import { LocationRoutingModule } from './location-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ComponentsModule } from 'src/app/shared/components/components.module';
import { AgmCoreModule } from "@agm/core";
import { SelectModule } from '../../select/select.module';
import { EditLocationComponent } from './edit-location/edit-location.component';
import { LocationDeleteDialogComponent } from './location-delete-dialog/location-delete-dialog.component';
import { LocationDetailsComponent } from './location-details/location-details.component';
import { RoomComponent } from './room/room/room.component';
import { RoomFormDialogComponent } from './room/room-form-dialog/room-form-dialog.component';



@NgModule({
  declarations: [
    LocationComponent,
    AddLocationComponent,
    EditLocationComponent,
    LocationDeleteDialogComponent,
    LocationDetailsComponent,
    RoomComponent,
    RoomFormDialogComponent
  ],
  imports: [
    CommonModule,
    LocationRoutingModule,
    SharedModule,
    ComponentsModule,
    AgmCoreModule,
    SelectModule
  ]
})
export class LocationModule { }
