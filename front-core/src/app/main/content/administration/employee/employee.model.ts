export class Employee {
    id: number;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    date: string;
    imageUrl: string;
    phone: string;
    userType: string;
    city: string;
    street: string;
    houseNumber: string;
    zip:number;
  
    constructor(employee) {
      
      this.id = employee.id;
      this.firstName = employee.name;
      this.lastName = employee.email;
      this.username = employee.email;
      this.email = employee.email;
      this.date =  "";
      this.imageUrl = employee.imageUrl;
      this.phone = employee.phone;
      this.userType = employee.userType;
      this.city = employee.city;
      this.street = employee.street;
      this.houseNumber = employee.houseNumber;
      this.zip = employee.zip;
      
    }
  }