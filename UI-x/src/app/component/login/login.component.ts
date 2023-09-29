import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit
{

  loginForm: FormGroup = new FormGroup(
    {
      userName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });


  constructor(private auth: LoginService, private router: Router, private toastrService: ToastrService) {}

  ngOnInit(): void
  {
    if(this.auth.isLoggedIn())
    {
      const role = this.auth.getRole();
      if(role === 'ADMIN')
      {
        this.router.navigateByUrl('admin');
      }
      else if(role === 'USER')
      {
        this.router.navigateByUrl('user');
      }
      else
      {
        console.log("No session");
      }
    }
  }

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
            this.toastrService.error('Error, No such user');
          }
      });
    }
    else
    {
      this.toastrService.error("Invalid login data")
    }
  }
}
