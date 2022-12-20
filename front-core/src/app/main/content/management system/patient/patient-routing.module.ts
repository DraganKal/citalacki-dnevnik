import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PatientDetailsComponent } from './patient-details/patient-details.component';
import { PatientComponent } from './patient/patient.component';
const routes: Routes = [
    {
      path: "",
      component: PatientComponent,
    },
    {
      path: ":id",
      component: PatientDetailsComponent,
    },
    // { path: "**", component: Page404Component },
  ];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class PatientRoutingModule {}

