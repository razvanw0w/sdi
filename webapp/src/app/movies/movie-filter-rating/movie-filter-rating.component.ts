import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-filter-rating',
  templateUrl: './movie-filter-rating.component.html',
  styleUrls: ['./movie-filter-rating.component.css']
})
export class MovieFilterRatingComponent implements OnInit {
  selectedMovie: Movie;
  movies: Movie[];
  movieForm: FormGroup;
  desiredRating: number;
  pageForm: FormGroup;

  constructor(private movieService: MovieService) {
  }

  get rating() {
    return this.movieForm.get('rating');
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.movieForm = new FormGroup({
      'rating': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.max(100),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
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

  goodRating(element: Movie, index, array): Boolean {
    return (element.rating) >= this.desiredRating;
  }

  filterByRating(rating: string, page: string, size: string): void {
    this.desiredRating = +rating;
    this.movieService.getMoviesPaginated(+page, +size).subscribe(dto => this.movies = dto.movies.filter(movie => movie.rating >= this.desiredRating));
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
}
