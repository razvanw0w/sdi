import {Component, OnInit} from '@angular/core';
import {Rental} from "../shared/rental.model";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.css']
})
export class RentalListComponent implements OnInit {
  rentals: Rental[];
  selectedRental: Rental;

  constructor(private rentalService: RentalService) {
  }

  ngOnInit(): void {
    this.rentalService.getRentals().subscribe(dto => this.rentals = dto.rentals);
  }

  getAllPaginated(page: string, size: string): void {
    this.rentalService.getRentalsPaginated(+page, +size).subscribe(dto => this.rentals = dto.rentals);
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }
}
