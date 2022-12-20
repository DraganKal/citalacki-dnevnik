import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { RouteAccessService } from "src/app/core/service/route-access.service";
import { READ } from "../constants";
import { UserProfileComponent } from "./user-profile/user-profile.component";

const routes: Routes = [
    {
      path: '',
      component: UserProfileComponent,
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
  export class UserRoutingModule {}
  