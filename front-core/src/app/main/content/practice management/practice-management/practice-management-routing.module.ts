import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PracticeManagementEditComponent } from './practice-management-edit/practice-management-edit.component';
import { PracticeManagementComponent } from './practice-management/practice-management.component';
const routes: Routes = [
    {
      path: ":id",
      component: PracticeManagementEditComponent,
    },
    {
      path: "",
      component: PracticeManagementComponent,
    },
    // { path: "**", component: Page404Component },
  ];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class PracticeManagementRoutingModule {}

