import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../shared/client.service";

@Component({
  selector: 'app-client-delete',
  templateUrl: './client-delete.component.html',
  styleUrls: ['./client-delete.component.css']
})
export class ClientDeleteComponent implements OnInit {
  clientForm: FormGroup
  successfulDelete: Boolean

  constructor(private clientService: ClientService) {
  }

  get id() {
    return this.clientForm.get('id');
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
      'id': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }

  delete(id: string): void {
    this.clientService.deleteClient(+id).subscribe(response => this.successfulDelete = response.status === 200);
  }
}
