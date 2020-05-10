import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../shared/client.service";

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  clientForm: FormGroup
  successfulAdd: Boolean

  constructor(private clientService: ClientService) {
  }

  get id() {
    return this.clientForm.get('id');
  }

  get name() {
    return this.clientForm.get('name');
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
      'id': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'name': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ])
    });
  }

  add(id: string, name: string): void {
    console.log(id, name);
    this.clientService.addClient({
      id: +id,
      name
    }).subscribe(response => this.successfulAdd = response.status === 200);
  }
}
