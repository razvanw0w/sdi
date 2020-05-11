import {Component, OnInit} from '@angular/core';
import {Rental} from "../shared/rental.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-filter',
  templateUrl: './rental-filter.component.html',
  styleUrls: ['./rental-filter.component.css']
})
export class RentalFilterComponent implements OnInit {
  selectedRental: Rental;
  rentals: Rental[];
  rentalForm: FormGroup;

  constructor(private rentalService: RentalService) {
  }

  get movieName() {
    return this.rentalForm.get('movieName');
  }

  ngOnInit(): void {
    this.rentalForm = new FormGroup({
      'movieName': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z0-9]+$")
      ])
    })
  }

  filterByMovieName(movieName: string): void {
    this.rentalService.filterRentalsByMovieName(movieName).subscribe(dto => this.rentals = dto.rentals);
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }
}

