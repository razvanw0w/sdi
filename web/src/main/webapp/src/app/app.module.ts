import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ClientsComponent} from './clients/clients.component';
import {ClientListComponent} from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MoviesComponent} from './movies/movies.component';
import {MovieListComponent} from './movies/movie-list/movie-list.component';
import {MovieService} from "./movies/shared/movie.service";
import {RentalsComponent} from './rentals/rentals.component';
import {RentalListComponent} from './rentals/rental-list/rental-list.component';
import {RentalService} from "./rentals/shared/rental.service";
import {ClientAddComponent} from './clients/client-add/client-add.component';
import {ClientUpdateComponent} from './clients/client-update/client-update.component';
import {ClientDeleteComponent} from './clients/client-delete/client-delete.component';
import {MovieAddComponent} from './movies/movie-add/movie-add.component';
import {MovieDeleteComponent} from './movies/movie-delete/movie-delete.component';
import {MovieUpdateComponent} from './movies/movie-update/movie-update.component';
import {RentalAddComponent} from './rentals/rental-add/rental-add.component';
import {RentalDeleteComponent} from './rentals/rental-delete/rental-delete.component';
import {RentalUpdateComponent} from './rentals/rental-update/rental-update.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    ClientListComponent,
    MoviesComponent,
    MovieListComponent,
    RentalsComponent,
    RentalListComponent,
    ClientAddComponent,
    ClientUpdateComponent,
    ClientDeleteComponent,
    MovieAddComponent,
    MovieDeleteComponent,
    MovieUpdateComponent,
    RentalAddComponent,
    RentalDeleteComponent,
    RentalUpdateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [ClientService, MovieService, RentalService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
