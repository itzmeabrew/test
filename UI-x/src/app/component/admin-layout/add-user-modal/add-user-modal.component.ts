import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-add-user-modal',
  templateUrl: './add-user-modal.component.html',
  styleUrls: ['./add-user-modal.component.css']
})
export class AddUserModalComponent
{
  editFrom: FormGroup = new FormGroup(
    {
      userName: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });

  constructor(public activeModal: NgbActiveModal, private admin: AdminService, private toastrService: ToastrService) {}

  public addUser(): void
  {
    console.log(this.editFrom.value);
    let data = this.editFrom.value;
    data.role = "USER";;

    if(this.editFrom.valid)
    {
      this.admin.addUser(data).subscribe({
        next: (res) =>
        {
          this.toastrService.success("User Added")
          this.activeModal.close();
        },
        error: (err) =>
        {
          this.toastrService.error("Error")
          console.error(err);
        }
      });
    }

  }
}
