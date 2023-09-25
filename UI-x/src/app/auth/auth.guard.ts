import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) =>
{
  const auth: LoginService = inject(LoginService);
  const router: Router = inject(Router);

  const accessToken = auth.getAccessToken();
  const  role = auth.getRole();

  if (accessToken)
  {
    if (role === "ADMIN" && route.url[0].path === "admin")
    {
      return true;
    }
    else if (role === "USER" && route.url[0].path === "user")
    {
      return true;
    }
    else
    {
      router.navigateByUrl("");
      return false;
    }
  }
  else
  {
    router.navigateByUrl("");
    return false;
  }
};
