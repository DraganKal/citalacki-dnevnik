import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Room } from './room.model';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http: HttpClient) { }

  getAllRoomsByPracticeLocationId(practiceLocationId: number) {
    return this.http.get(environment.apiUrl + '/practice-location/' + practiceLocationId + '/rooms');
  }

  saveRoom(room: Room){
    return this.http.post(environment.apiUrl +'/room', room);
  }

}
