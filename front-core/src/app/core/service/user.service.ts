import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { 
  }

  getCurrentUser() {
    return this.httpClient.get(`${environment.apiUrl}/user`);
  }

  setUser(data: any) {
    localStorage.setItem('user', JSON.stringify(data));
  }

  sendPasswordsToUsers(usersIds){
    return this.httpClient.post(environment.apiUrl + '/user/send-credentials-to-all', usersIds);
  }

  setPassword(data) {
    return this.httpClient.post(environment.apiUrl + '/user/reset-password', data);
  }

  getUserById(userId: number) {
    return this.httpClient.get(`${environment.apiUrl}/user/` + userId);
  }


  updateUser(userId: number, user: User) {
    return this.httpClient.put(`${environment.apiUrl}/user/` + userId, user);
  }

  updatePassword(userId, userPassword: any) {
    return this.httpClient.post(environment.apiUrl + '/user/'+ userId + '/update-password', userPassword);
  }

  sendResetPasswordLink(email) {
    return this.httpClient.post(environment.apiUrl + '/user/send-reset-password-email', email);
  }

  uploadImage(id, image) {
    return  this.httpClient.post(environment.apiUrl +  '/logopedic-practice/' + id + '/logo', image );
   }

  uploadProfileImage(userId: number, image) {
    return  this.httpClient.post(environment.apiUrl +  '/user/' + userId + '/profile-image', image );
  } 

  getProfileImage(userId: number) {
    return this.httpClient.get(environment.apiUrl + '/user/'+ userId + '/profile-image', {
      responseType: 'blob'
    });
  }

}
