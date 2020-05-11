import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
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

  ngOnInit(): void {
    this.sortForm = new FormGroup({
      'name': new FormControl("N/A"),
      'genre': new FormControl("N/A"),
      'rating': new FormControl("N/A")
    })
  }

  sort(nameDirection: string, genreDirection: string, ratingDirection: string): void {
    this.movieService.sortMovies(nameDirection, genreDirection, ratingDirection)
      .subscribe(dto => this.movies = dto.movies);
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }
}
