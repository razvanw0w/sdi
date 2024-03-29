import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {
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
