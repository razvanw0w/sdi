import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-update',
  templateUrl: './movie-update.component.html',
  styleUrls: ['./movie-update.component.css']
})
export class MovieUpdateComponent implements OnInit {
  movieForm: FormGroup
  successfulUpdate: Boolean

  constructor(private movieService: MovieService) {
  }

  get id() {
    return this.movieForm.get('id');
  }

  get name() {
    return this.movieForm.get('name');
  }

  get genre() {
    return this.movieForm.get('genre');
  }

  get rating() {
    return this.movieForm.get('rating');
  }

  ngOnInit(): void {
    this.movieForm = new FormGroup({
      'id': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'name': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z0-9]+$")
      ]),
      'genre': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ]),
      'rating': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.max(100),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }

  update(id: string, name: string, genre: string, rating: string): void {
    this.movieService.updateMovie({
      id: +id,
      name,
      genre,
      rating: +rating
    }).subscribe(response => this.successfulUpdate = response.status === 200);
  }
}
