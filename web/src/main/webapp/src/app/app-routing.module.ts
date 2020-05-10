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


const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'clients/list', component: ClientListComponent},
  {path: 'clients/add', component: ClientAddComponent},
  {path: 'clients/update', component: ClientUpdateComponent},
  {path: 'clients/delete', component: ClientDeleteComponent},
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
