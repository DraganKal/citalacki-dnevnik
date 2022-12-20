import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Employee } from './employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }

  getAllEmployees(){
    return this.http.get(environment.apiUrl + '/employee');
  }

  public saveEmployee(employee:Employee){
    return this.http.post(environment.apiUrl +'/employee', employee);
  }


  deleteEmployee(employeeId: number) {
    return this.http.delete(environment.apiUrl + '/employee/' + employeeId);
  }

  updateEmployee(employeeId:number, employee:Employee){
    return this.http.put(environment.apiUrl +'/employee/' + employeeId, employee);
  }


}
