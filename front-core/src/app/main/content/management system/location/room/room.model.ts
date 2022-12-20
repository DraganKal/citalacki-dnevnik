export class Room {
    public id: number;
    public name: string;
    public description: string;
    public colorHex: string;
    public logopedicPracticeId: number;
    public logopedicPracticeName: string;
    public practiceLocationId: number;
    public practiceLocationCode: string;
    public practiceLocationDescription: string;
  
    constructor(room) {  
      this.id = room.id;
      this.name = room.room;
      this.description = room.description,
      this.colorHex = room.colorHex;
      this.logopedicPracticeId = room.logopedicPracticeId;
      this.logopedicPracticeName = room.logopedicPracticeName;
      this.practiceLocationId = room.practiceLocationId;
      this.practiceLocationCode = room.practiceLocationCode;
      this.practiceLocationDescription = room.practiceLocationDescription;  
    }
  }