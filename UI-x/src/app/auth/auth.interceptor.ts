import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError ,throwError} from 'rxjs';
import { LoginService } from '../service/login.service';
import { Router, RouterModule } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor
{

  constructor(private auth: LoginService, private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    const accessToken = this.auth.getAccessToken();
    let newRequest:HttpRequest<any> = null;

    // debugger
    if(!request.url.includes("api/admin/login"))
    {
      if (accessToken)
      {
        newRequest = request.clone({
          setHeaders:
          {
            Authorization: 'Bearer ' + accessToken,
          }
        });
      }
      else
      {
        this.router.navigateByUrl('');
      }
    }
    else
    {
      newRequest = request.clone();
    }


    // Pass the request on to the next handler
    return next.handle(newRequest).pipe(catchError((error: HttpErrorResponse) =>
    {
      if (error.status === 401)
      {
        this.auth.logOut();  // Unauthorized status code, log out the user
        this.router.navigateByUrl(""); // Redirect to the login page
      }
      console.log(error);

      return throwError(() => error);
    }));
  }
}
