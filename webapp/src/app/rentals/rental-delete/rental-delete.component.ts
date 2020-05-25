import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-delete',
  templateUrl: './rental-delete.component.html',
  styleUrls: ['./rental-delete.component.css']
})
export class RentalDeleteComponent implements OnInit {
  rentalForm: FormGroup;
  successfulDelete: Boolean;

  constructor(private rentalService: RentalService) {

  }

  get rentalId() {
    return this.rentalForm.get('rentalId');
  }

  ngOnInit(): void {
    this.rentalForm = new FormGroup({
      'rentalId': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }

  delete(rentalId: string): void {
    this.rentalService.deleteRental(+rentalId).subscribe(response => this.successfulDelete = response.status === 200);
  }
}
