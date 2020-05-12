import {Component, OnInit} from '@angular/core';
import {RentedMovie} from "./rentedmovie.model";
import {ReportService} from "../shared/report.service";

@Component({
  selector: 'app-report-topmovies',
  templateUrl: './report-topmovies.component.html',
  styleUrls: ['./report-topmovies.component.css']
})
export class ReportTopmoviesComponent implements OnInit {
  movies: RentedMovie[];

  constructor(private reportService: ReportService) {
  }

  ngOnInit(): void {
    this.reportService.getTopRentedMovies().subscribe(dto => this.movies = dto.rentedMovieStatistics);
  }

}
