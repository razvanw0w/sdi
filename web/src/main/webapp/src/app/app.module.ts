import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ClientsComponent} from './clients/clients.component';
import {ClientListComponent} from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MoviesComponent} from './movies/movies.component';
import {MovieListComponent} from './movies/movie-list/movie-list.component';
import {MovieService} from "./movies/shared/movie.service";
import {RentalsComponent} from './rentals/rentals.component';
import {RentalListComponent} from './rentals/rental-list/rental-list.component';
import {RentalService} from "./rentals/shared/rental.service";

@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    ClientListComponent,
    MoviesComponent,
    MovieListComponent,
    RentalsComponent,
    RentalListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ClientService, MovieService, RentalService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
