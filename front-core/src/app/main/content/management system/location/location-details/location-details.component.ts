import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '../location.model';
import { LocationService } from '../location.service';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.sass']
})
export class LocationDetailsComponent implements OnInit {

  locationId: number;
  location: Location;

  latitude: number;
  longitude : number;
  markLatitude : number;
  markLongitude : number;
  zoom = 15

  constructor(private locationService: LocationService,
              private route: ActivatedRoute,) { }

  ngOnInit(): void {
    this.getData();
  }

  private getData() {
    this.locationId  = this.route.snapshot.params.id;
    this.locationService.getLocation(this.locationId).subscribe((location: Location) => {
      this.location = location;
      this.loadMap();
    });
  }

  private loadMap() {
    this.latitude = this.location.latitude;
    this.longitude = this.location.longitude;
    this.markLatitude = this.location.latitude;
    this.markLongitude = this.location.longitude;
  }

}
