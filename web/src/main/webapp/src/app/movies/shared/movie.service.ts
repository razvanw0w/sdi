import {HttpClient, HttpResponse} from "@angular/common/http";
import {Movie, Movies} from "./movie.model";
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

  addMovie(movie: Movie): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.moviesURL, movie, {observe: "response"});
  }

  updateMovie(movie: Movie): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(`${this.moviesURL}/${movie.id}`, movie, {observe: "response"});
  }

  deleteMovie(id: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<HttpResponse<any>>(`${this.moviesURL}/${id}`, {observe: "response"});
  }
}

