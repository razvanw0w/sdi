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
    return this.httpClient.get<Rentals>(this.rentalsURL, {withCredentials: true});
  }

  getRentalsPaginated(page: number, size: number): Observable<Rentals> {
    return this.httpClient.get<Rentals>(`${this.rentalsURL}/page=${page}&size=${size}`);
  }

  addRental(rental: Rental): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.rentalsURL, rental, {observe: "response", withCredentials: true});
  }

  updateRental(rental: Rental): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(`${this.rentalsURL}/${rental.rentalId}`, rental, {
      observe: "response",
      withCredentials: true
    });
  }

  deleteRental(rentalId: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<HttpResponse<any>>(`${this.rentalsURL}/${rentalId}`, {observe: "response", withCredentials: true});
  }

  filterRentalsByMovieName(movieName: string): Observable<Rentals> {
    return this.httpClient.get<Rentals>(`${this.rentalsURL}/filter/${movieName}`, {withCredentials: true});
  }

  filterRentalsByDate(date: string): Observable<Rentals> {
    return this.httpClient.get<Rentals>(`${this.rentalsURL}/filter/date/${date}`);
  }

  filterRentalsByDatePaginated(date: string, page: number, size: number) {
    return this.httpClient.get<Rentals>(`${this.rentalsURL}/filter-paginated/date/${date}&page=${page}&size=${size}`);
  }
}
