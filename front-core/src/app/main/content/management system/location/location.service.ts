import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Location } from './location.model';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) { }

  addLocation(location: Location) {
    return this.http.post(environment.apiUrl +'/practice-location', location);
  }

  getAllLocations() {
    return this.http.get(environment.apiUrl + '/practice-location');
  }

  getLocation(locationId: number) {
    return this.http.get(environment.apiUrl + '/practice-location/' + locationId);
  }

  updateLocation(locationId:number, location:Location){
    return this.http.put(environment.apiUrl +'/practice-location/' + locationId, location);
  }

  deleteLocation(locationId: number) {
    return this.http.delete(environment.apiUrl + '/practice-location/' + locationId);
  }


} 
