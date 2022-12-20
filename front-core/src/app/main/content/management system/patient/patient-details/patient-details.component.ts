import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Contact } from '../../contact/contact.model';
import { Patient } from '../patient.model';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {
  selection = new SelectionModel<Contact>(true, []);
  patientId: number;
  patient: Patient;
  contacts: Contact[];

  contactElements: any;
  contactDataSource: MatTableDataSource<Contact>;

  displayedColumns = [
    "firstName",
    "lastName",
    "contactType",
    "phone",
    "email",
    "actions"
  ];


  constructor(private patientService: PatientService,
              private route: ActivatedRoute,) { }

  ngOnInit(): void {
    this.getData();
  }
  
  private getData() {
    this.patientId  = this.route.snapshot.params.id;
    this.patientService.getOne(this.patientId).subscribe((patient: Patient) => {
      this.patient = patient;
    });
  }

}
