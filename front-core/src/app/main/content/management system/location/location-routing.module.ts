import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { RouteAccessService } from "src/app/core/service/route-access.service";
import { READ, WRITE } from "../../constants";
import { AddLocationComponent } from "./add-location/add-location.component";
import { EditLocationComponent } from "./edit-location/edit-location.component";
import { LocationDetailsComponent } from "./location-details/location-details.component";
import { LocationComponent } from "./location/location.component";

const routes: Routes = [
    {
      path: '',
      component: LocationComponent,
      data: {
        authorities: [{name:'employee', right:READ}],
      },
      canActivate: [RouteAccessService]
    },
    {
      path: 'add-location',
      component: AddLocationComponent,
      data: {
        authorities: [{name:'employee', right:WRITE}],
      },
      canActivate: [RouteAccessService]
    },
    {
      path: 'edit/:id',
      component: EditLocationComponent,
      data: {
        authorities: [{name:'employee', right:WRITE}],
      },
      canActivate: [RouteAccessService]
    },
    {
      path: ':id',
      component: LocationDetailsComponent,
      data: {
        authorities: [{name:'employee', right:WRITE}],
      },
      canActivate: [RouteAccessService]
    },
  ];
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class LocationRoutingModule {}