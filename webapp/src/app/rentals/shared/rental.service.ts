import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Rental, Rentals} from "./rental.model";

@Injectable()
export class RentalService {
  private rentalsURL = 'http://localhost:8080/api/rentals';

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rentals> {
    return this.httpClient.get<Rentals>(this.rentalsURL);
  }

  addRental(rental: Rental): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.rentalsURL, rental, {observe: "response"});
  }

  updateRental(rental: Rental): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(`${this.rentalsURL}/${rental.movieId}&${rental.clientId}`, rental, {observe: "response"});
  }

  deleteRental(movieId: number, clientId: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<HttpResponse<any>>(`${this.rentalsURL}/${movieId}&${clientId}`, {observe: "response"});
  }

  filterRentalsByMovieName(movieName: string): Observable<Rentals> {
    return this.httpClient.get<Rentals>(`${this.rentalsURL}/filter/${movieName}`);
  }

  filterRentalsByDate(date: string): Observable<Rentals> {
    return this.httpClient.get<Rentals>(`${this.rentalsURL}/filter/date/${date}`);
  }
}
