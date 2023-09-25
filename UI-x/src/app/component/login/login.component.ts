import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
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


  constructor(private auth: LoginService){}

  public login(): void
  {
    // console.log(this.loginForm.value);
    const loginData = this.loginForm.value;
    this.auth.login(loginData).subscribe((response) =>
    {
      console.log(response);
    },
    (error) =>
    {
      console.error(error);
    });

  }
}
