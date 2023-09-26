import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/service/admin.service';
import { User } from 'src/app/model/user';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { EditModalComponent } from './edit-modal/edit-modal.component';
import { ToastrService } from 'ngx-toastr';
import { AddUserModalComponent } from './add-user-modal/add-user-modal.component';
@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.css']
})
export class AdminLayoutComponent implements OnInit
{

  userList: User[] = new Array();

  constructor(config: NgbModalConfig, private admnin: AdminService, private modalService: NgbModal, private toastrService: ToastrService)
  {
    config.backdrop = 'static';
  }


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
          console.log("User loaded");

          this.userList.length = 0;
          const users: Array<any> = res.data;

          users.forEach(user =>
            {
              this.userList.push(user);
            });
        },
        error: err =>
        {

        }
      });



  }

  public openEdit(user: User): void
  {
		const modalRef = this.modalService.open(EditModalComponent);
    modalRef.componentInstance.userData = user;
    modalRef.result.then((res)=>
    {
      this.getAllUsers();
    });
	}

  public openAdd(): void
  {
    const modalRef = this.modalService.open(AddUserModalComponent).result.then(
			(result) =>
      {
        this.getAllUsers();
			});
  }

  public deleteUser(user: User): void
  {
    const id: number = user.id;

    this.admnin.deleteUser(id).subscribe({
      next: (res) =>
      {
        this.toastrService.success("User Deleted")
        this.getAllUsers();
      },
      error: (err) =>
      {
        this.toastrService.error("Error")
        console.error(err);
      }
    });
  }

}
