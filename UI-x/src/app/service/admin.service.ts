import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService
{

  ENDPOINT = "api/admin/";

  constructor(private http: HttpClient) { }

  public getAllUsers(): Observable<any>
  {
    // return this.http.get("http://localhost:3000");

    return this.http.get(this.ENDPOINT + "/users");
  }
}
