import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent
{

  loginForm: FormGroup = new FormGroup(
    {
      userName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });


  constructor(private auth: LoginService, private router: Router, private toastrService: ToastrService) {}

  public login(): void
  {
    console.log("Login");
    const loginData = this.loginForm.value;
    if (this.loginForm.valid)
    {
      this.auth.login(loginData).subscribe(
        {
          next: (response) =>
          {
            const data = response.data;
            const status = response.status;
            const role = data.role[0];

            this.auth.setAccessToken(data.accessToken);
            this.auth.setRole(role);

            if (role === 'ADMIN')
            {
              this.router.navigateByUrl('admin');
            }
            else
            {
              this.router.navigateByUrl('user');
            }
          },
          error: (err) =>
          {
            console.error(err);
            this.toastrService.error('Error');
          }
      });
    }
    else
    {
      this.toastrService.error("Invalid login data")
    }
  }
}
