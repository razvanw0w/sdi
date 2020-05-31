import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
    withCredentials: true,
    observe: 'response'
  };
  currentUser: string = "NONE";
  private url = 'http://localhost:8080/login';

  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string): Observable<boolean> {
    return this.http.post(`${this.url}?username=${username}&password=${password}`, {}, {
      headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
      withCredentials: true,
      observe: 'response'
    })
      .pipe(
        map(result => {
          this.getCurrentUser().subscribe(credentialsLogin => this.currentUser = credentialsLogin);
          return result.status === 200;
        })
      );
  }

  getCurrentUser(): Observable<string> {
    return this.http.get("http://localhost:8080/api/user", {withCredentials: true})
      .pipe(
        map(response => {
          return response["type"];
        })
      );
  }

  logout(): Observable<any> {
    return this.http.post("http://localhost:8080/logout", {}, {
      headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
      withCredentials: true,
      observe: 'response'
    });
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
