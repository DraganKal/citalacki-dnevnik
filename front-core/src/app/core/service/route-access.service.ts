import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RouteAccessService implements CanActivate {

  constructor(private authenticationService: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    const authorities:Array<any> = route.data['authorities'];
    if (!authorities || authorities.length === 0) {
      return true;
    }
    
    for(let authority of authorities){
      if(this.authenticationService.checkPermission(authority.name, authority.right, authority.role)){
        return true;
      }  
    }
    
    return false;
   
  }

}
