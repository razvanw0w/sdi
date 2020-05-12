import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {MovieService} from "../shared/movie.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  movies: Movie[];
  selectedMovie: Movie;
  pageForm: FormGroup;

  constructor(private movieService: MovieService) {
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.pageForm = new FormGroup({
      'page': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'size': new FormControl("", [
        Validators.required,
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
    //this.movieService.getMovies().subscribe(dto => this.movies = dto.movies);
  }

  getAllPaginated(page: string, size: string) {
    this.movieService.getMoviesPaginated(+page, +size).subscribe(dto => this.movies = dto.movies);
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
}
