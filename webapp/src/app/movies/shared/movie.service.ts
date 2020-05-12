import {HttpClient, HttpResponse} from "@angular/common/http";
import {Movie, Movies} from "./movie.model";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {Sort, SortUnit} from "../movie-sort/model/sort.model";

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

  filterMoviesByGenre(genre: string): Observable<Movies> {
    return this.httpClient.get<Movies>(`${this.moviesURL}/filter/${genre}`);
  }

  sortMovies(nameDirection: string, genreDirection: string, ratingDirection: string): Observable<Movies> {
    var directions = new Array<SortUnit>();
    console.log(nameDirection, genreDirection, ratingDirection);
    if (nameDirection !== "N/A") {
      console.log("here1");
      directions.push({
        direction: nameDirection,
        field: 'name'
      });
    }
    if (genreDirection !== "N/A") {
      console.log("here2");
      directions.push({
        direction: genreDirection,
        field: 'genre'
      });
    }
    if (ratingDirection !== "N/A") {
      console.log("here3");
      directions.push({
        direction: ratingDirection,
        field: 'rating'
      });
    }
    var sort = new Sort();
    sort.units = directions;
    console.log(directions);
    console.log(sort);
    return this.httpClient.post<Movies>(`${this.moviesURL}/sort`, sort);
  }
}
