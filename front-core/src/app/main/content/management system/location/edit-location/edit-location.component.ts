import { MapsAPILoader } from '@agm/core';
import { Component, ElementRef, NgZone, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RightSidebarComponent } from 'src/app/layout/right-sidebar/right-sidebar.component';
import { UtilService } from 'src/app/main/util.service';
import { LogopedicPractice } from '../../../practice management/practice-management/logopedic-practice.model';
import { PracticeManagementService } from '../../../practice management/practice-management/practice-management.service';
import { Location } from '../location.model';
import { LocationService } from '../location.service';

@Component({
  selector: 'app-edit-location',
  templateUrl: './edit-location.component.html',
  styleUrls: ['./edit-location.component.sass']
})
export class EditLocationComponent implements OnInit {

  location = new Location({});
  locationId: number;

  locationForm: FormGroup;

  latitude: number;
  longitude : number;
  markLatitude : number;
  markLongitude : number;
  zoom = 15

  address: string;
  private geoCoder;

  practices : LogopedicPractice[] = new Array;

  @ViewChild('search')
  public searchElementRef: ElementRef;
  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar,
              private mapsAPILoader: MapsAPILoader,
              private ngZone: NgZone,
              private logopedicPracticeService: PracticeManagementService,
              private locationService: LocationService,
              private utilService: UtilService) { }

  ngOnInit(): void {
    this.createLocationForm();
    this.getLogopedicPractices();
    this.getLocation();
    this.loadMap();
  }

  getLocation() {
    const urlArray = String(window.location.href).split("/");
    this.locationId = Number(urlArray[6]);
    this.locationService.getLocation(this.locationId).subscribe(((res: Location) => {
      this.location = res;
      this.setLocationForm();
    }))
  }

  private setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.latitude = this.location.latitude;
        this.longitude = this.location.longitude;
        this.markLatitude = this.location.latitude;
        this.markLongitude = this.location.longitude;
        this.zoom = 15;
      });
    }
  }

  private loadMap() {
    this.mapsAPILoader.load().then(() => {
      this.setCurrentLocation();
      this.geoCoder = new google.maps.Geocoder;

      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement);
      autocomplete.addListener("place_changed", () => {
        this.ngZone.run(() => {
          //get the place result
          let place: google.maps.places.PlaceResult = autocomplete.getPlace();

          //verify result
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }

          //set latitude, longitude and zoom
          this.latitude = place.geometry.location.lat();
          this.longitude = place.geometry.location.lng();
          this.markLatitude = place.geometry.location.lat();
          this.markLongitude = place.geometry.location.lng();
          this.zoom = 15;
        });
      });
    });
  }

  getLogopedicPractices() {
    this.logopedicPracticeService.getAll().subscribe( (res: LogopedicPractice[]) => {
      this.practices = res;
    })
  }

  createLocationForm() {
    this.locationForm = this.formBuilder.group({
      status: ['', Validators.required],
      locationType: ['', Validators.required],
      practice: [, Validators.required],
      code: ['', Validators.required],
      description: [''],
      street: [''],
      houseNumber: [''],
      zip: [''],
      city: [''],
      main: [true]
    });
  }

  setLocationForm() {
    this.locationForm.controls["status"].setValue(this.location.status);
    this.locationForm.controls["locationType"].setValue(this.location.locationType);
    this.locationForm.controls["practice"].setValue(this.location.practiceId);
    this.locationForm.controls["code"].setValue(this.location.code);
    this.locationForm.controls["description"].setValue(this.location.description);
    this.locationForm.controls["street"].setValue(this.location.street);
    this.locationForm.controls["houseNumber"].setValue(this.location.houseNumber);
    this.locationForm.controls["zip"].setValue(this.location.zip);
    this.locationForm.controls["city"].setValue(this.location.city);
    this.locationForm.controls["main"].setValue(this.location.main);
  }

  placeMarker(event) {
    this.markLatitude = event.coords.lat;
    this.markLongitude = event.coords.lng;
    this.getAddress(this.latitude, this.longitude);
    this.zoom = 15
  }

  getAddress(latitude, longitude) {
    this.geoCoder.geocode({ 'location': { lat: latitude, lng: longitude } }, (results, status) => {
      if (status === 'OK') {
        if (results[0]) {
          this.zoom = 15;
          this.address = results[0].formatted_address;
        } else {
          window.alert('No results found');
        }
      } else {
        window.alert('Geocoder failed due to: ' + status);
      }

    });
  }
  
  isClosed() {
    if(this.locationForm.controls["status"].value === "CLOSED") {
      return false;
    }
    return true;
  }

  isPracticeNotSelected() {
    if(this.locationForm.controls['practice'].value == undefined) {
      return true;
    }
    return false;
  }

  submit() {
    this.location.status = this.locationForm.controls['status'].value;
    this.location.locationType = this.locationForm.controls['locationType'].value;
    this.location.practiceId = this.locationForm.controls['practice'].value;
    this.location.code = this.locationForm.controls['code'].value;
    this.location.description = this.locationForm.controls['description'].value;
    this.location.street = this.locationForm.controls['street'].value;
    this.location.houseNumber = this.locationForm.controls['houseNumber'].value;
    this.location.zip = this.locationForm.controls['zip'].value;
    this.location.city = this.locationForm.controls['city'].value;
    this.location.main = this.locationForm.controls['main'].value;
    this.location.latitude = this.markLatitude;
    this.location.longitude = this.markLongitude;

    this.locationService.updateLocation(this.location.id, this.location).subscribe({
      next: () => {
        this.utilService.showNotification(
          "snackbar-success",
          "Location Edited Successfully...!!!",
          "bottom",
          "center"
        );
        this.router.navigateByUrl("/locations");  
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
