import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanDeactivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import {ConfigService} from "./services/config.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private service: ConfigService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    console.log("usao")
    // this.router.navigate(['login'])


    // localStorage.setItem('token', '08dfcbc47e1b42e8b11e8c26afbd2279');
    console.log('token:' + this.service.getToken()+ '.');

    if (this.service.getToken() === localStorage.getItem('token')){
      console.log(localStorage.getItem('token'));
      return true;
    }

    return false;
  }

}
