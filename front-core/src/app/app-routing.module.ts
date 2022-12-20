import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Page404Component } from './authentication/page404/page404.component';
import { AuthGuard } from './core/guard/auth.guard';
import { Role } from './core/models/role';
import { RouteAccessService } from './core/service/route-access.service';
import { AuthLayoutComponent } from './layout/app-layout/auth-layout/auth-layout.component';
import { MainLayoutComponent } from './layout/app-layout/main-layout/main-layout.component';
const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [RouteAccessService],
    children: [
      { path: '', redirectTo: '/authentication/signin', pathMatch: 'full' },
      {
        path: 'extra-pages',
        canActivate: [RouteAccessService],
        loadChildren: () =>
          import('./extra-pages/extra-pages.module').then(
            (m) => m.ExtraPagesModule
          ),
      },
      {
        path: "patient",
        canActivate: [AuthGuard],
        loadChildren: () =>
          import("./main/content/management system/patient/patient.module").then((m) => m.PatientModule),
      },
      {
        path: 'administration',
        canActivate: [RouteAccessService],
        loadChildren: () =>
          import('./main/content/administration/employee/employee.module').then(
            (m) => m.EmployeeModule
          ),
      },
      {
        path: 'user-profile',
        canActivate: [RouteAccessService],
        loadChildren: () =>
          import('./main/content/user/user.module').then(
            (m) => m.UserModule
          ),
      },
      {
        path: "practice-management",
        canActivate: [RouteAccessService],
        loadChildren: () =>
          import("./main/content/practice management/practice-management/practice-management.module").then((m) => m.PracticeManagementModule),
      },
      {
        path: 'locations',
        canActivate: [RouteAccessService],
        loadChildren: () =>
          import('./main/content/management system/location/location.module').then(
            (m) => m.LocationModule
          ),
      },
      {
        path: "contact",
        canActivate: [RouteAccessService],
        loadChildren: () =>
          import("./main/content/management system/contact/contact.module").then((m) => m.ContactModule),
      },
    ],
  },
  {
    path: 'authentication',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('./authentication/authentication.module').then(
        (m) => m.AuthenticationModule
      ),
  },
  { path: '**', component: Page404Component },
];
@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
