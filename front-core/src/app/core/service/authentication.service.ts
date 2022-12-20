import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) { }

  public login(data: any): any {
    return this.httpClient.post(`${environment.apiUrl}/authenticate`, data);
  }

  public logout(): void {
    this.removeToken();
    this.removeUser();
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  removeToken(): void {
    localStorage.removeItem('token');
  }

  removeUser() {
    localStorage.removeItem('user');
  }

  getUser() {
    if (localStorage.getItem('user') != null) {
      return JSON.parse(localStorage.getItem('user'));
    }
    return null;
  }

  checkPermission(viewName, right, role?) {
    let user = this.getUser();
    if (user != null && user != '') {
      for (let i = 0; i < user.userGroups.length; i++) {
        for (let j = 0; j < user.userGroups[i].userPermissions.length; j++) {
          const permission = user.userGroups[i].userPermissions[j];
          if (viewName === permission.viewName  && right === permission.viewRight && (role ? permission.userViewRole === role : true) ) {
            return true;
          }
        }
      }
    }
    return false;
  }

  clear() {
    localStorage.clear();
  }

  validateTokenForResetPassword(token) {
    return this.httpClient.post(environment.apiUrl + "/user/validate-token", token);
  }

}
