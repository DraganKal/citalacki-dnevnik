export class Practice{
    id: number;
    name: string;
    logo: any;
    constructor(practice) {
        {
            this.id = practice.id;
            this.name = practice.name || "";
            this.logo = practice.logo || "";
        }
    }
}