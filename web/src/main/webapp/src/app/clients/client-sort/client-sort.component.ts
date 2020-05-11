import {Component, OnInit} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";

@Component({
  selector: 'app-client-sort',
  templateUrl: './client-sort.component.html',
  styleUrls: ['./client-sort.component.css']
})
export class ClientSortComponent implements OnInit {
  clients: Client[];
  selectedClient: Client;

  constructor(private clientService: ClientService) {
  }

  ngOnInit(): void {
    this.clientService.getClients().subscribe(dto => this.clients = dto.clients.sort((a, b) => a.name.localeCompare(b.name)));
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }
}
