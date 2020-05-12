import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-delete',
  templateUrl: './movie-delete.component.html',
  styleUrls: ['./movie-delete.component.css']
})
export class MovieDeleteComponent implements OnInit {
  movieForm: FormGroup
  successfulDelete: Boolean

  constructor(private movieService: MovieService) {
  }

  get id() {
    return this.movieForm.get('id');
  }

  ngOnInit(): void {
    this.movieForm = new FormGroup({
      'id': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }

  delete(id: string): void {
    this.movieService.deleteMovie(+id).subscribe(response => this.successfulDelete = response.status === 200);
  }
}
