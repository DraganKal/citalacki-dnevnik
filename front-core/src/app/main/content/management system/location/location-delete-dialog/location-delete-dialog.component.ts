import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UtilService } from 'src/app/main/util.service';
import { Location } from '../location.model';
import { LocationService } from '../location.service';

@Component({
  selector: 'app-location-delete-dialog',
  templateUrl: './location-delete-dialog.component.html',
  styleUrls: ['./location-delete-dialog.component.sass']
})
export class LocationDeleteDialogComponent {

  location: Location;

  constructor(public dialogRef: MatDialogRef<LocationDeleteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public locationService: LocationService,
              private utilService: UtilService) {
    this.location = data.location;
   }


  close(): void {
    this.dialogRef.close();
  }
  confirmDelete(): void {
    this.locationService.deleteLocation(this.location.id).subscribe({
      next: () => {
        this.utilService.showNotification(
          "snackbar-success",
          "Location deleted Successfully!",
          "bottom",
          "center"
        );
        this.dialogRef.close(1)
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
