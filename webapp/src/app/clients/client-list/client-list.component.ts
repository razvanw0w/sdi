import {Component, OnInit} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: Client[];
  selectedClient: Client;
  pageForm: FormGroup;

  constructor(private clientService: ClientService) {
  }

  get page() {
    return this.pageForm.get('page');
  }

  get size() {
    return this.pageForm.get('size');
  }

  ngOnInit(): void {
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
    //this.clientService.getClients().subscribe(dto => this.clients = dto.clients);
  }

  getAllPaginated(page: string, size: string) {
    this.clientService.getClientsPaginated(+page, +size).subscribe(dto => this.clients = dto.clients);
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }
}
