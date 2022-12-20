export class Patient {
    id: number;
    firstName: string;
    lastName: string;
    phone: string;
    fixedPatientNumber: number;
    city: string;
    houseNumber: string;
    street: string;
    zip: number;
    email: string;
    code: string;
    constructor(patient) {
      {
        this.id = patient.id;
        this.firstName = patient.firstName || "";
        this.lastName = patient.lastName || "";
        this.phone = patient.phone || "";
        this.fixedPatientNumber = patient.fixedPatientNumber || "";
        this.city = patient.city || "";
        this.houseNumber = patient.houseNumber || "";
        this.street = patient.street || "";
        this.zip = patient.zip || "";
        this.email = patient.email || "";
        this.code = patient.code || "";
      }
    }
}