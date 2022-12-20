import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Practice } from './practice.model';

@Injectable({
  providedIn: 'root'
})
export class PracticeManagementService {

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(environment.apiUrl + '/logopedic-practice');
  }

  add(patient: Practice) {
    return this.http.post(environment.apiUrl + '/logopedic-practice', patient);
  }

  getOne(id: number) {
    return this.http.get(environment.apiUrl + '/logopedic-practice/'+ id);
  }

  getLogo(id: number) {
    return this.http.get(environment.apiUrl + '/logopedic-practice/'+ id+ '/logo', {
      responseType: 'blob'
    });
  }
  put(id: number, practice) {
    return this.http.put(environment.apiUrl + '/logopedic-practice/'+ id, practice);
  }

  delete(id: number) {
    return this.http.delete(environment.apiUrl + '/logopedic-practice/' + id);
  }

}
