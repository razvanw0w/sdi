import {Component, OnInit} from '@angular/core';
import {Rental} from '../shared/rental.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RentalService} from '../shared/rental.service';

@Component({
  selector: 'app-rental-filter-client',
  templateUrl: './rental-filter-client.component.html',
  styleUrls: ['./rental-filter-client.component.css']
})
export class RentalFilterClientComponent implements OnInit {
  selectedRental: Rental;
  rentals: Rental[];
  rentalForm: FormGroup;
  dateRegex = "^(((((0[1-9])|(1\\d)|(2[0-8]))-((0[1-9])|(1[0-2])))|((31-((0[13578])|(1[02])))|((29|30)-((0[1,3-9])|(1[0-2])))))-((20[0-9][0-9]))|(29-02-20(([02468][048])|([13579][26]))))$";

  constructor(private rentalService: RentalService) {
  }

  get date() {
    return this.rentalForm.get('date');
  }

  ngOnInit(): void {
    this.rentalForm = new FormGroup({
      date: new FormControl('', [
        Validators.required,
        Validators.pattern(this.dateRegex),
      ])
    });
  }

  filterByDate(date: string): void {
    this.rentalService.filterRentalsByDate(date).subscribe(dto => this.rentals = dto.rentals);
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }
}
