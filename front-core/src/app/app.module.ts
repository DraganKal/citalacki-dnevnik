import { NgModule } from "@angular/core";

import { CoreModule } from "./core/core.module";
import { SharedModule } from "./shared/shared.module";

import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HeaderComponent } from "./layout/header/header.component";
import { PageLoaderComponent } from "./layout/page-loader/page-loader.component";
import { SidebarComponent } from "./layout/sidebar/sidebar.component";
import { RightSidebarComponent } from "./layout/right-sidebar/right-sidebar.component";
import { AuthLayoutComponent } from "./layout/app-layout/auth-layout/auth-layout.component";
import { MainLayoutComponent } from "./layout/app-layout/main-layout/main-layout.component";
import { fakeBackendProvider } from "./core/interceptor/fake-backend";
import { ErrorInterceptor } from "./core/interceptor/error.interceptor";
import { JwtInterceptor } from "./core/interceptor/jwt.interceptor";
import { LocationStrategy, HashLocationStrategy } from "@angular/common";
import {
  PerfectScrollbarModule,
  PERFECT_SCROLLBAR_CONFIG,
  PerfectScrollbarConfigInterface,
} from "ngx-perfect-scrollbar";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { ClickOutsideModule } from "ng-click-outside";
import {
  HttpClientModule,
  HTTP_INTERCEPTORS,
  HttpClient,
} from "@angular/common/http";

import { LoadingBarRouterModule } from "@ngx-loading-bar/router";
import { TestComponent } from './main/content/test/test.component';
import { EmployeeModule } from "./main/content/administration/employee/employee.module";
import { SetPasswordComponent } from "./authentication/set-password/set-password.component";
import { AgmCoreModule } from "@agm/core";

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
  wheelPropagation: false,
};

export function createTranslateLoader(http: HttpClient): any {
  return new TranslateHttpLoader(http, "assets/i18n/", ".json");
}

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        PageLoaderComponent,
        SidebarComponent,
        RightSidebarComponent,
        AuthLayoutComponent,
        MainLayoutComponent,
        TestComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        PerfectScrollbarModule,
        ClickOutsideModule,
        LoadingBarRouterModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient],
            },
        }),
        // core & shared
        CoreModule,
        SharedModule,
        AgmCoreModule.forRoot({
          apiKey: 'AIzaSyA1JSGjWgEnhKKHV0HMsz8Bha5BuQPEM9g',
          libraries: ['places']
        })
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        {
            provide: PERFECT_SCROLLBAR_CONFIG,
            useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
        },
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        fakeBackendProvider,
    ],
    bootstrap: [AppComponent]
})
export class AppModule {}
