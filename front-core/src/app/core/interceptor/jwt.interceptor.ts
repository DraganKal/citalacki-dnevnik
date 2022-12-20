import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthenticationService } from '../service/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  ignoredRoutes = [
      '/authentication',];

  constructor(private authenticationService: AuthenticationService,
              private snackBar: MatSnackBar,
              private router: Router) {}

  addToken(request: HttpRequest<any>): HttpRequest<any> {
    const accessToken = this.authenticationService.getToken();
    if (accessToken) {
      return request.clone({
        headers: request.headers.set('Authorization', `Bearer ${accessToken}`)
      })
    } else {
      return request;
    }
  }

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const requestUrl = request.url;

    if (!this.isIgnoredRoute(requestUrl)) {
      return next.handle(this.addToken(request)).pipe(
        catchError((error: HttpErrorResponse) => {
          console.log("--------------- ERROR ----------------");
          console.log(error);
          if (error.status === 401) {
            this.authenticationService.logout();
          } else if (error.status === 500) {
            // this.router.navigateByUrl('500');
            this.showNotification(
              "snackbar-danger",
              "SOMETHING_WENT_WRONG",
              "bottom",
              "center"
            );
          } else if (error.status === 404) {
            this.router.navigateByUrl("page404");
          } else if (error.status === 0) {
            console.log(error);
            //this.loginService.logout();
          } else {
            if (error.error instanceof Blob) {
              const reader: FileReader = new FileReader();
              reader.onloadend = e => {
                this.showNotification(
                  "snackbar-danger",
                  JSON.parse(reader.result as string).message,
                  "bottom",
                  "center"
                );
              };
              reader.readAsText(error.error);
            } else {
              let errorText;
              if (!error.error.message) {
                errorText =
                  JSON.parse(error.error).message || "SOMETHING_WENT_WRONG";
              }
              this.showNotification(
                "snackbar-danger",
                errorText ? errorText : error.error.message,
                "bottom",
                "center"
              );
            }
            return throwError(error);
          }
        })
      );
    } else {
      return next.handle(request);
    }
  }

  isIgnoredRoute(requestUrl) {
    return !!this.ignoredRoutes.find(route => requestUrl.includes(route));
  }

  showNotification(colorName, text, placementFrom, placementAlign) {
    this.snackBar.open(text, "", {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }

}
