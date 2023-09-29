import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/service/login.service';

@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.css']
})
export class AddAdminComponent
{
  adminForm: FormGroup = new FormGroup(
    {
      userName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });


  constructor(private auth: LoginService, private router: Router, private toastrService: ToastrService) {}

  public login(): void
  {
    console.log("Login");
    let loginData = this.adminForm.value;
    loginData.role = "ADMIN";

    if (this.adminForm.valid)
    {
      this.auth.addAdmin(loginData).subscribe(
        {
          next: (response) =>
          {
            this.toastrService.success('Adnmin user added successfully');
          },
          error: (err) =>
          {
            console.error(err);
            this.toastrService.error('Error, User already exists');
          }
      });
    }
    else
    {
      this.toastrService.error("Invalid login data")
    }
  }

}


