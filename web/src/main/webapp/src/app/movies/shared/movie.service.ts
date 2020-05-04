import {HttpClient} from "@angular/common/http";
import {Movies} from "./movie.model";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class MovieService {
  private moviesURL = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovies(): Observable<Movies> {
    return this.httpClient.get<Movies>(this.moviesURL);
  }
}
