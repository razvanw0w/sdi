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
  pageForm: FormGroup;

  constructor(private clientService: ClientService) {
  }

  get name() {
    return this.clientForm.get('name');
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
      'name': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ])
    });
    this.pageForm = new FormGroup({
      'page': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'size': new FormControl("", [
        Validators.required,
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }

  filterByName(name: string, page: string, size: string): void {
    console.log(name, page, size);
    this.clientService.filterClientsByNamePaginated(name, +page, +size).subscribe(dto => this.clients = dto.clients);
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }
}
