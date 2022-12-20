import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators';
import { User } from '../models/user';
import { Role } from '../models/role';

const users: User[] = [
  {
    id: 1,
    img: 'assets/images/user/admin.jpg',
    username: 'admin@hospital.org',
    password: 'admin@123',
    firstName: 'Sarah',
    lastName: 'Smith',
    role: Role.ADMINISTRATOR,
    token: 'admin-token',
  },
  {
    id: 2,
    img: 'assets/images/user/doctor.jpg',
    username: 'doctor@hospital.org',
    password: 'doctor@123',
    firstName: 'Ashton',
    lastName: 'Cox',
    role: Role.PRACTICE_MANAGER,
    token: 'doctor-token',
  },
  {
    id: 3,
    img: 'assets/images/user/patient.jpg',
    username: 'patient@hospital.org',
    password: 'patient@123',
    firstName: 'Cara',
    lastName: 'Stevens',
    role: Role.EMPLOYEE,
    token: 'patient-token',
  },
];

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request);
  }
}

export let fakeBackendProvider = {
  // use fake backend in place of Http service for backend-less development
  provide: HTTP_INTERCEPTORS,
  useClass: FakeBackendInterceptor,
  multi: true,
};
