import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Contact } from './contact.model';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient) { }

  getAll(){
    return this.http.get(environment.apiUrl + '/contact');
  }

  public save(contact: Contact){
    return this.http.post(environment.apiUrl +'/contact', contact);
  }

  update(contactId: number, contact: Contact){
    return this.http.put(environment.apiUrl +'/contact/' + contactId, contact);
   }

  delete(contactId: number) {
    return this.http.delete(environment.apiUrl + '/contact/' + contactId);
  }

  getAllContactsByPatientId(patientId: number) {
    return this.http.get(environment.apiUrl + '/patient/' + patientId + '/contact');
  }

  public saveContactToPatient(patientId: number, contact: Contact){
    return this.http.post(environment.apiUrl +'/patient/' + patientId + '/contact', contact);
  }


}
