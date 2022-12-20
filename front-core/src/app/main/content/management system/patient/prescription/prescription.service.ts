import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Prescription } from './prescription.model';

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {

  constructor(private http: HttpClient) { }

  getAllByPatientId(patientId: number) {
    return this.http.get(environment.apiUrl + '/patient/' + patientId + '/prescription');
  }

  getOne(patientId: number, prescriptionId: number) {
    return this.http.get(environment.apiUrl + '/patient/' + patientId + '/prescription/' + prescriptionId);
  }

  public save(patientId: number, prescription: Prescription){
    return this.http.post(environment.apiUrl + '/patient/' + patientId + '/prescription', prescription);
  }

  update(patientId: number, prescriptionId: number, prescription: Prescription){
    return this.http.put(environment.apiUrl + '/patient/' + patientId + '/prescription/' + prescriptionId, prescription);
   }

  delete(patientId: number, prescriptionId: number) {
    return this.http.delete(environment.apiUrl + '/patient/' + patientId + '/prescription/' + prescriptionId);
  }

}
