import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-update',
  templateUrl: './rental-update.component.html',
  styleUrls: ['./rental-update.component.css']
})
export class RentalUpdateComponent implements OnInit {
  rentalForm: FormGroup
  successfulUpdate: Boolean
  dateRegex: string = "^(((((0[1-9])|(1\\d)|(2[0-8]))-((0[1-9])|(1[0-2])))|((31-((0[13578])|(1[02])))|((29|30)-((0[1,3-9])|(1[0-2])))))-((20[0-9][0-9]))|(29-02-20(([02468][048])|([13579][26]))))$";
  timeRegex: string = "^(([1-9]{1})|([0-1][0-9])|([1-2][0-3])):([0-5][0-9])$";

  constructor(private rentalService: RentalService) {

  }

  get movieId() {
    return this.rentalForm.get('movieId');
  }

  get clientId() {
    return this.rentalForm.get('clientId');
  }

  get time() {
    return this.rentalForm.get('time');
  }

  get date() {
    return this.rentalForm.get('date');
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
      ]),

      'date': new FormControl("", [
        Validators.required,
        Validators.pattern(this.dateRegex)
      ]),
      'time': new FormControl("", [
        Validators.required,
        Validators.pattern(this.timeRegex)
      ]),
    });
  }

  update(rentalId: string, date: string, time: string): void {
    var datetime = `${date} ${time}`;
    console.log(rentalId, datetime);
    this.rentalService.updateRental({
      rentalId: +rentalId,
      movieId: 0,
      clientId: 0,
      time: datetime
    }).subscribe(response => this.successfulUpdate = response.status === 200);
  }
}
