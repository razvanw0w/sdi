import {Component, OnInit} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-client-filter',
  templateUrl: './client-filter.component.html',
  styleUrls: ['./client-filter.component.css']
})
export class ClientFilterComponent implements OnInit {
  clients: Client[];
  clientForm: FormGroup;
  selectedClient: Client;

  constructor(private clientService: ClientService) {
  }

  get name() {
    return this.clientForm.get('name');
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
      'name': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ])
    });
  }

  filterByName(name: string): void {
    this.clientService.filterClientsByName(name).subscribe(dto => this.clients = dto.clients);
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }
}
