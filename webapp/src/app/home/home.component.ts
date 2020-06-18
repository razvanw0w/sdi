import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  title = 'Movie rental application';
  employeeRole = false;

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(): void {
    this.loginService.getCurrentUser().subscribe(result => {
      console.log("home init " + result);
      this.employeeRole = (result === 'ROLE_EMPLOYEE');
    });
    this.employeeRole = this.loginService.currentUser === 'ROLE_EMPLOYEE';
  }

  logout(): void {
    console.log("log out frontend");
    this.loginService.logout().subscribe(result => console.log(result));
    this.router.navigate(["login"]);
  }
}
