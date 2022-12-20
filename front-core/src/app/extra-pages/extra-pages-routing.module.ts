import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RouteAccessService } from '../core/service/route-access.service';
import { READ } from '../main/content/constants';
import { BlankComponent } from './blank/blank.component';
const routes: Routes = [
  {
    path: 'blank',
    component: BlankComponent,
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
export class ExtraPagesRoutingModule {}
