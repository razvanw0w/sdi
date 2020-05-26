import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-filter-genre',
  templateUrl: './movie-filter-genre.component.html',
  styleUrls: ['./movie-filter-genre.component.css']
})
export class MovieFilterGenreComponent implements OnInit {
  selectedMovie: Movie;
  movies: Movie[];
  movieForm: FormGroup;
  pageForm: FormGroup;

  constructor(private movieService: MovieService) {
  }

  get genre() {
    return this.movieForm.get('genre');
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.movieForm = new FormGroup({
      'genre': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ])
    });
  }

  filterByGenre(genre: string): void {
    this.movieService.filterMoviesByGenre(genre).subscribe(dto => this.movies = dto.movies);
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
}
