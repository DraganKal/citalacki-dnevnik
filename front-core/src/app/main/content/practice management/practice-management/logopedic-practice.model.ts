export class LogopedicPractice {
    id: number;
    name: string;
    logoUrl: string;
    colorHex1: string;
    colorHex2: string;
    constructor(logopedicPractice) {
      {
        this.id = logopedicPractice.id;
        this.name = logopedicPractice.name || "";
        this.logoUrl = logopedicPractice.logoUrl || "";
        this.colorHex1 = logopedicPractice.colorHex1 || "";
        this.colorHex2 = logopedicPractice.colorHex2 || "";
      }
    }
}