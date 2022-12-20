import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {environment} from '../../../../../environments/environment';
import { Patient } from './patient.model';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) {
  }

  getAll(pageIndex, pageSize, sortActive, sortDirection) {
    return this.http.get(
      environment.apiUrl + '/patient' , {
        observe: 'response',
        params: {
          pageIndex: pageIndex,
          pageSize: pageSize,
          sortActive: sortActive,
          sortDirection: sortDirection
        }
      });
  }

  getOne(id: number) {
    return this.http.get(environment.apiUrl + '/patient/'+ id);
  }

  addPatient(patient: Patient) {
    return this.http.post(environment.apiUrl + '/patient', patient);
  }

  putPatient(patientId: number, patient: Patient) {
    return this.http.put(environment.apiUrl + '/patient/'+ patientId, patient);
  }

  deletePatient(patientId: number) {
    return this.http.delete(environment.apiUrl + '/patient/' + patientId);
  }

}
