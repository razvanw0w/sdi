import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {ClientListComponent} from "./clients/client-list/client-list.component";
import {MoviesComponent} from "./movies/movies.component";
import {MovieListComponent} from "./movies/movie-list/movie-list.component";
import {RentalsComponent} from "./rentals/rentals.component";
import {RentalListComponent} from "./rentals/rental-list/rental-list.component";
import {ClientAddComponent} from "./clients/client-add/client-add.component";
import {ClientUpdateComponent} from "./clients/client-update/client-update.component";
import {ClientDeleteComponent} from "./clients/client-delete/client-delete.component";
import {MovieAddComponent} from "./movies/movie-add/movie-add.component";
import {MovieDeleteComponent} from "./movies/movie-delete/movie-delete.component";
import {MovieUpdateComponent} from "./movies/movie-update/movie-update.component";
import {RentalAddComponent} from "./rentals/rental-add/rental-add.component";
import {RentalUpdateComponent} from "./rentals/rental-update/rental-update.component";
import {RentalDeleteComponent} from "./rentals/rental-delete/rental-delete.component";
import {ReportsComponent} from "./reports/reports.component";
import {ReportTopmoviesComponent} from "./reports/report-topmovies/report-topmovies.component";
import {ReportClientgenresComponent} from "./reports/report-clientgenres/report-clientgenres.component";
import {ClientFilterComponent} from "./clients/client-filter/client-filter.component";
import {MovieFilterGenreComponent} from "./movies/movie-filter-genre/movie-filter-genre.component";
import {MovieFilterRatingComponent} from "./movies/movie-filter-rating/movie-filter-rating.component";
import {RentalFilterComponent} from "./rentals/rental-filter/rental-filter.component";


const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'clients/list', component: ClientListComponent},
  {path: 'clients/add', component: ClientAddComponent},
  {path: 'clients/update', component: ClientUpdateComponent},
  {path: 'clients/delete', component: ClientDeleteComponent},
  {path: 'clients/filter/name', component: ClientFilterComponent},
  {path: 'movies', component: MoviesComponent},
  {path: 'movies/list', component: MovieListComponent},
  {path: 'movies/add', component: MovieAddComponent},
  {path: 'movies/delete', component: MovieDeleteComponent},
  {path: 'movies/update', component: MovieUpdateComponent},
  {path: 'movies/filter/genre', component: MovieFilterGenreComponent},
  {path: 'movies/filter/rating', component: MovieFilterRatingComponent},
  {path: 'rentals', component: RentalsComponent},
  {path: 'rentals/list', component: RentalListComponent},
  {path: 'rentals/add', component: RentalAddComponent},
  {path: 'rentals/update', component: RentalUpdateComponent},
  {path: 'rentals/delete', component: RentalDeleteComponent},
  {path: 'rentals/filter/moviename', component: RentalFilterComponent},
  {path: 'reports', component: ReportsComponent},
  {path: 'reports/topmovies', component: ReportTopmoviesComponent},
  {path: 'reports/clientgenres', component: ReportClientgenresComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
