import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService
{

  ENDPOINT = "/api/admin/";

  constructor(private http: HttpClient) { }

  public login(form: any): Observable<any>
  {
    return this.http.post(this.ENDPOINT + "login", form);
  }

  public addAdmin(admin: any): Observable<any>
  {
    return this.http.post(this.ENDPOINT + "register", admin);
  }

  public setAccessToken(accessToken: string): void
  {
    localStorage.setItem("accessToken", accessToken);
  }

  public getAccessToken(): string
  {
    return localStorage.getItem ("accessToken");
  }

  public logOut(): void
  {
    localStorage.clear();
  }

  public isLoggedIn(): boolean
  {
    if (localStorage.getItem("accessToken") && localStorage.getItem("role"))
    {
      return true;
    }
    else
    {
      return false;
    }

  }

  public setRole(role: string): void
  {
    localStorage.setItem("role", role);
  }

  public getRole(): string
  {
    return localStorage.getItem("role");
  }
}
