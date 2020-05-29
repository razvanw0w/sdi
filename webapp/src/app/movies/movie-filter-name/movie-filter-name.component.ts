import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-filter-name',
  templateUrl: './movie-filter-name.component.html',
  styleUrls: ['./movie-filter-name.component.css']
})
export class MovieFilterNameComponent implements OnInit {
  selectedMovie: Movie;
  movies: Movie[];
  movieForm: FormGroup;
  pageForm: FormGroup;

  constructor(private movieService: MovieService) {
  }

  get name() {
    return this.movieForm.get('name');
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.movieForm = new FormGroup({
      'name': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z0-9]+$")
      ])
    });
  }

  filterByName(name: string): void {
    this.movieService.filterMoviesByName(name).subscribe(dto => this.movies = dto.movies);
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
}
