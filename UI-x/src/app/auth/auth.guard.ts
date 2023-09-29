import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

export const authGuard: CanActivateFn = (route, state) =>
{
  const auth: LoginService = inject(LoginService);
  const router: Router = inject(Router);
  const tost: ToastrService = inject(ToastrService);

  const accessToken = auth.getAccessToken();
  const role = auth.getRole();

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
      tost.error("Session expired, Login Again");
      router.navigateByUrl("");
      return false;
    }
  }
  else
  {
    tost.error("No session,please login");
    router.navigateByUrl("");
    return false;
  }
};
