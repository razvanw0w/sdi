import {Component, OnInit} from '@angular/core';
import {Rental} from "../shared/rental.model";
import {RentalService} from "../shared/rental.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.css']
})
export class RentalListComponent implements OnInit {
  rentals: Rental[];
  selectedRental: Rental;
  pageForm: FormGroup;

  constructor(private rentalService: RentalService) {
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.pageForm = new FormGroup({
      'page': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'size': new FormControl("", [
        Validators.required,
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
    //this.rentalService.getRentals().subscribe(dto => this.rentals = dto.rentals);
  }

  getAllPaginated(page: string, size: string): void {
    this.rentalService.getRentalsPaginated(+page, +size).subscribe(dto => this.rentals = dto.rentals);
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }
}
