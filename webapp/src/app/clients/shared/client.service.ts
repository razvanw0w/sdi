import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client, Clients} from "./client.model";

@Injectable()
export class ClientService {
  private clientsURL = 'http://localhost:8080/api/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Clients> {
    return this.httpClient.get<Clients>(this.clientsURL);
  }

  getClientsPaginated(page: number, size: number): Observable<Clients> {
    return this.httpClient.get<Clients>(`${this.clientsURL}/page=${page}&size=${size}`);
  }

  addClient(client: Client): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.clientsURL, client, {observe: "response"});
  }

  updateClient(client: Client): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(`${this.clientsURL}/${client.id}`, client, {observe: "response"});
  }

  deleteClient(id: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete(`${this.clientsURL}/${id}`, {observe: "response"});
  }

  filterClientsByName(name: string): Observable<Clients> {
    return this.httpClient.get<Clients>(`${this.clientsURL}/filter-name/${name}`);
  }

  filterClientsByFidelity(fidelity: number): Observable<Clients> {
    return this.httpClient.get<Clients>(`${this.clientsURL}/filter-fidelity/${fidelity}`);
  }

  filterClientsByNamePaginated(name: string, page: number, size: number): Observable<Clients> {
    console.log("paginated filter", name, page, size);
    return this.httpClient.get<Clients>(`${this.clientsURL}/filter-paginated/${name}&page=${page}&size=${size}`);
  }
}
