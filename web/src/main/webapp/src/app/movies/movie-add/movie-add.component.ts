import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MovieService} from "../../movies/shared/movie.service";

@Component({
  selector: 'app-movie-add',
  templateUrl: './movie-add.component.html',
  styleUrls: ['./movie-add.component.css']
})
export class MovieAddComponent implements OnInit {
  movieForm: FormGroup
  successfulAdd: Boolean

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

  add(id: string, name: string, genre: string, rating: string): void {
    this.movieService.addMovie({
      id: +id,
      name,
      genre,
      rating: +rating
    }).subscribe(response => this.successfulAdd = response.status === 200);
  }
}
