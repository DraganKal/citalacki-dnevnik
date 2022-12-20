export class Contact {
    id: number;
    firstName: string;
    lastName: string;
    contactType: string;
    city: string;
    houseNumber: string;
    street: string;
    zip:number;
    phone: string;
    email: string;
    contactTypeOther: string;
    note: string;

    constructor(contact) {   
      this.id = contact.id;
      this.firstName = contact.firstName;
      this.lastName = contact.lastName;
      this.contactType = contact.contactType;
      this.contactTypeOther = contact.contactTypeOther;
      this.city = contact.city;
      this.houseNumber =  contact.houseNumber;
      this.street = contact.street;
      this.zip = contact.zip;
      this.phone = contact.phone;
      this.email = contact.email;
      this.note = contact.note;  
    }
  }
  