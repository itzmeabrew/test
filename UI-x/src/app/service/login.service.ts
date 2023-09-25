import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService
{

  ENDPOINT = "localhost:8080/api/admin/";

  constructor(private http: HttpClient) { }

  public login(form: any): Observable<any>
  {
    return this.http.post(this.ENDPOINT + "login", form);
  }
}
