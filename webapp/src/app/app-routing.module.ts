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
import {ClientSortComponent} from "./clients/client-sort/client-sort.component";
import {MovieSortComponent} from "./movies/movie-sort/movie-sort.component";
import {RentalFilterClientComponent} from "./rentals/rental-filter-client/rental-filter-client.component";
import {ClientFilterFidelityComponent} from "./clients/client-filter-fidelity/client-filter-fidelity.component";
import {MovieFilterNameComponent} from "./movies/movie-filter-name/movie-filter-name.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {EmployeeGuard} from "./employee-auth";
import {AuthGuard} from "./basic-auth";


const routes: Routes = [
  {path: 'clients', component: ClientsComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/list', component: ClientListComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/add', component: ClientAddComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/update', component: ClientUpdateComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/delete', component: ClientDeleteComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/filter/name', component: ClientFilterComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/filter/fidelity', component: ClientFilterFidelityComponent, canActivate: [EmployeeGuard]},
  {path: 'clients/sort', component: ClientSortComponent, canActivate: [EmployeeGuard]},
  {path: 'movies', component: MoviesComponent, canActivate: [AuthGuard]},
  {path: 'movies/list', component: MovieListComponent, canActivate: [AuthGuard]},
  {path: 'movies/add', component: MovieAddComponent, canActivate: [EmployeeGuard]},
  {path: 'movies/delete', component: MovieDeleteComponent, canActivate: [EmployeeGuard]},
  {path: 'movies/update', component: MovieUpdateComponent, canActivate: [EmployeeGuard]},
  {path: 'movies/filter/genre', component: MovieFilterGenreComponent, canActivate: [AuthGuard]},
  {path: 'movies/filter/rating', component: MovieFilterRatingComponent, canActivate: [AuthGuard]},
  {path: 'movies/filter/name', component: MovieFilterNameComponent, canActivate: [AuthGuard]},
  {path: 'movies/sort', component: MovieSortComponent, canActivate: [AuthGuard]},
  {path: 'rentals', component: RentalsComponent, canActivate: [EmployeeGuard]},
  {path: 'rentals/list', component: RentalListComponent, canActivate: [EmployeeGuard]},
  {path: 'rentals/add', component: RentalAddComponent, canActivate: [EmployeeGuard]},
  {path: 'rentals/update', component: RentalUpdateComponent, canActivate: [EmployeeGuard]},
  {path: 'rentals/delete', component: RentalDeleteComponent, canActivate: [EmployeeGuard]},
  {path: 'rentals/filter/moviename', component: RentalFilterComponent, canActivate: [EmployeeGuard]},
  {path: 'rentals/filter/date', component: RentalFilterClientComponent, canActivate: [EmployeeGuard]},
  {path: 'reports', component: ReportsComponent, canActivate: [AuthGuard]},
  {path: 'reports/topmovies', component: ReportTopmoviesComponent, canActivate: [AuthGuard]},
  {path: 'reports/clientgenres', component: ReportClientgenresComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: '**', redirectTo: 'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
