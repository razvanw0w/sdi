import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../shared/client.service";

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  clientForm: FormGroup;
  successfulAdd: Boolean;

  constructor(private clientService: ClientService) {
  }

  get name() {
    return this.clientForm.get('name');
  }

  get fidelity() {
    return this.clientForm.get('fidelity');
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
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

  add(name: string, fidelity: string): void {
    console.log(name);
    this.clientService.addClient({
      id: 1,
      name,
      fidelity: +fidelity
    }).subscribe(response => this.successfulAdd = response.status === 200);
  }
}
