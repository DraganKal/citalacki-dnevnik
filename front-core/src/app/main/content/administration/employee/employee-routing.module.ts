import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { RouteAccessService } from "src/app/core/service/route-access.service";
import { READ } from "../../constants";
import { EmployeeComponent } from "./employee/employee.component";

const routes: Routes = [
    {
      path: 'employee',
      component: EmployeeComponent,
      data: {
        authorities: [{name:'employee', right:READ}],
      },
      canActivate: [RouteAccessService]
    },
  ];
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class EmployeeRoutingModule {}
  