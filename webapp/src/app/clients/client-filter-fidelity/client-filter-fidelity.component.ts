import {Component, OnInit} from '@angular/core';
import {Client} from "../shared/client.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../shared/client.service";

@Component({
  selector: 'app-client-filter-fidelity',
  templateUrl: './client-filter-fidelity.component.html',
  styleUrls: ['./client-filter-fidelity.component.css']
})
export class ClientFilterFidelityComponent implements OnInit {
  clients: Client[];
  clientForm: FormGroup;
  selectedClient: Client;
  pageForm: FormGroup;

  constructor(private clientService: ClientService) {
  }

  get fidelity() {
    return this.clientForm.get('fidelity');
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
      'fidelity': new FormControl("", [
        Validators.required,
        Validators.min(1),
        Validators.max(5),
        Validators.pattern("^[1-9]+[0-9]*$")
      ])
    });
  }

  filterByFidelity(fidelity: string): void {
    console.log(fidelity);
    this.clientService.filterClientsByFidelity(+fidelity).subscribe(dto => this.clients = dto.clients);
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }
}
