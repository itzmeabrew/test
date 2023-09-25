import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService
{

  ENDPOINT = "api/admin";

  constructor(private http: HttpClient) { }

  public getAllUsers(): Observable<any>
  {
    // return this.http.get("http://localhost:3000");
    return this.http.get(this.ENDPOINT + "/users");
  }

  public addUser(user: any): Observable<any>
  {
    return this.http.post(this.ENDPOINT + "/createUser",user);
  }

  public editUser(edit: any, id: number): Observable<any>
  {
    return this.http.put(this.ENDPOINT + "/user/" + id, edit);
  }

  public deleteUser(id: number): Observable<any>
  {
    return this.http.delete(this.ENDPOINT + "/user/" + id);
  }
}
