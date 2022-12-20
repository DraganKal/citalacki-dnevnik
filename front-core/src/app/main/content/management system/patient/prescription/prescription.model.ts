export class Prescription {
    id: number;
    issuingDoctor: string;
    name: string;
    city: string;
    houseNumber: string;
    street: string;
    zip:number;
    healthInsuranceStatus: string;
    healthInsuranceNumber: string;
    history: string;
    numberOfRequiredAppointments: number;
    patientId: number;
    patientFirstName: string;
    patientLastName: string;

    constructor(prescription) {   
      this.id = prescription.id;
      this.issuingDoctor = prescription.issuingDoctor;
      this.name = prescription.name;
      this.city = prescription.city;
      this.houseNumber = prescription.houseNumber;
      this.street = prescription.street;
      this.healthInsuranceStatus =  prescription.healthInsuranceStatus;
      this.healthInsuranceNumber = prescription.healthInsuranceNumber;
      this.history = prescription.history;
      this.numberOfRequiredAppointments = prescription.numberOfRequiredAppointments;
      this.patientId = prescription.patientId;
      this.patientFirstName = prescription.patientFirstName;
      this.patientLastName = prescription.patientLastName;  
    }
  }