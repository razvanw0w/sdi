import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../shared/client.service";

@Component({
  selector: 'app-client-update',
  templateUrl: './client-update.component.html',
  styleUrls: ['./client-update.component.css']
})
export class ClientUpdateComponent implements OnInit {
  clientForm: FormGroup;
  successfulUpdate: Boolean;

  constructor(private clientService: ClientService) {
  }

  get id() {
    return this.clientForm.get('id');
  }

  get name() {
    return this.clientForm.get('name');
  }

  get fidelity() {
    return this.clientForm.get('fidelity');
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
      ]),
      'fidelity': new FormControl("", [
        Validators.required,
        Validators.min(1),
        Validators.max(5),
        Validators.pattern("^[1-9]+[0-9]*$")
      ])
    });
  }

  update(id: string, name: string, fidelity: string): void {
    console.log(id, name);
    this.clientService.updateClient({
      id: +id,
      name,
      fidelity: +fidelity
    }).subscribe(response => this.successfulUpdate = response.status === 200);
  }
}
