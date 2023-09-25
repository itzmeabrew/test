import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/service/admin.service';
import { User } from 'src/app/model/user';
@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.css']
})
export class AdminLayoutComponent implements OnInit
{

  userList: User[] = new Array();

  constructor(private admnin: AdminService){}


  ngOnInit(): void
  {
    this.getAllUsers();
  }

  private getAllUsers(): void
  {
    this.admnin.getAllUsers().subscribe(
      {
        next: res =>
        {
          const users: Array<any> = res.data;
          // console.log(userList);

          users.forEach(user =>
            {
              // console.log(user);
              // const newUser: User = {id: user.id, userName:user.userName, firstName:user.firstName, lastName:user.lastName, role:user.role}
              this.userList.push(user);
            });

            console.log(this.userList);

        },
        error: err =>
        {

        }
      });



  }

}
