export class Rental {
  rentalId: number;
  time: string;
  movieId: number;
  clientId: number;
}

export class Rentals {
  rentals: Array<Rental>;
}
