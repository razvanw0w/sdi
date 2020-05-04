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

  constructor(private rentalService: RentalService) {
  }

  ngOnInit(): void {
    this.rentalService.getRentals().subscribe(dto => this.rentals = dto.rentals);
  }

}
