import {HttpClient} from "@angular/common/http";
import {RentedMovies} from "../report-topmovies/rentedmovie.model";
import {Observable} from "rxjs";
import {ClientGenres} from "../report-clientgenres/clientgenre.model";
import {Injectable} from "@angular/core";

@Injectable()
export class ReportService {
  private reportsURL = 'http://localhost:8080/api/reports'

  constructor(private httpClient: HttpClient) {
  }

  getTopRentedMovies(): Observable<RentedMovies> {
    return this.httpClient.get<RentedMovies>(`${this.reportsURL}/movies`);
  }

  getClientGenres(): Observable<ClientGenres> {
    return this.httpClient.get<ClientGenres>(`${this.reportsURL}/genres`);
  }
}
