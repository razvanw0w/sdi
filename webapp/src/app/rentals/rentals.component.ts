import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-rentals',
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.css']
})
export class RentalsComponent implements OnInit {
  employeeRole = false;

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(): void {
    this.employeeRole = this.loginService.currentUser === 'ROLE_EMPLOYEE';
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(["login"]);
  }
}
