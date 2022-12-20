export class Location {
    public id: number;
    public code: string;
    public description: string;
    public locationType: string;
    public status: string;
    public street: string;
    public houseNumber: string;
    public zip: string;
    public city: string;
    public latitude: number;
    public longitude: number;
    public practiceId: number;
    // public practice: ;
    // public responsibleUserId: number;
    // public responsibleUserFullName: string;
    public main: boolean;
  
    constructor(location) {
      
      this.id = location.id;
      this.code = location.name;
      this.description = location.description,
      this.locationType = location.type;
      this.status = location.status;
      this.street = location.street;
      this.houseNumber = location.houseNumber;
      this.zip = location.zip;
      this.city = location.city;
      this.latitude = location.latitude;
      this.longitude = location.longitude;
      this.practiceId = location.practiceId;
      this.main = location.main;
      
    }
  }