import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Movie} from "../shared/movie.model";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-sort',
  templateUrl: './movie-sort.component.html',
  styleUrls: ['./movie-sort.component.css']
})
export class MovieSortComponent implements OnInit {
  sortForm: FormGroup;
  movies: Movie[];
  selectedMovie: Movie;
  pageForm: FormGroup;

  constructor(private movieService: MovieService) {
  }

  get name() {
    return this.sortForm.get('name');
  }

  get genre() {
    return this.sortForm.get('genre');
  }

  get rating() {
    return this.sortForm.get('rating');
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.sortForm = new FormGroup({
      'name': new FormControl("N/A"),
      'genre': new FormControl("N/A"),
      'rating': new FormControl("N/A")
    });
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
  }

  sort(nameDirection: string, genreDirection: string, ratingDirection: string, page: string, size: string): void {
    this.movieService.sortMoviesPaginated(nameDirection, genreDirection, ratingDirection, +page, +size)
      .subscribe(dto => this.movies = dto.movies);
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
}
