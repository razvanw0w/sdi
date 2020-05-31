import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoginService} from "./login/login.service";

@Injectable({providedIn: 'root'})
export class EmployeeGuard implements CanActivate {
  constructor(
    private router: Router,
    private loginService: LoginService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log("employee guard" + this.loginService.currentUser);
    if (this.loginService.currentUser !== "ROLE_EMPLOYEE") {
      this.router.navigateByUrl("/home");
      return false;
    } else {
      return true;
    }
  }
}
