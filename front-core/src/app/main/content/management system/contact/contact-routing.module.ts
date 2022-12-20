import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactDashboardComponent } from './contact-dashboard/contact-dashboard.component';
import { ContactComponent } from './contact/contact.component';
const routes: Routes = [
    {
      path: "",
      component: ContactDashboardComponent,
    },
    // { path: "**", component: Page404Component },
  ];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class ContactRoutingModule {}