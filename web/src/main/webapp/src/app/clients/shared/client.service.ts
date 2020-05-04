import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Clients} from "./client.model";

@Injectable()
export class ClientService {
  private clientsURL = 'http://localhost:8080/api/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Clients> {
    return this.httpClient.get<Clients>(this.clientsURL);
  }
}
