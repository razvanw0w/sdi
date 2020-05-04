import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Rentals} from "./rental.model";

@Injectable()
export class RentalService {
  private rentalsURL = 'http://localhost:8080/api/rentals';

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rentals> {
    return this.httpClient.get<Rentals>(this.rentalsURL);
  }
}
