import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {ClientListComponent} from "./clients/client-list/client-list.component";
import {MoviesComponent} from "./movies/movies.component";
import {MovieListComponent} from "./movies/movie-list/movie-list.component";
import {RentalsComponent} from "./rentals/rentals.component";
import {RentalListComponent} from "./rentals/rental-list/rental-list.component";


const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'clients/list', component: ClientListComponent},
  {path: 'movies', component: MoviesComponent},
  {path: 'movies/list', component: MovieListComponent},
  {path: 'rentals', component: RentalsComponent},
  {path: 'rentals/list', component: RentalListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
