import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService
{
  ENDPOINT = "/api/user/";

  constructor(private http: HttpClient) { }

  public getFiles(): Observable<any>
  {
    return this.http.get(this.ENDPOINT + "file");
  }

  public uploadFile(file: File): Observable<any>
  {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(this.ENDPOINT + "file", formData);
  }

  public deleteFile(id: number): Observable<any>
  {
    return this.http.delete(this.ENDPOINT + "file/" + id);
  }

}
