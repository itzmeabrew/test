import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '../../../service/admin.service';
import { User } from 'src/app/model/user';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-edit-modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./edit-modal.component.css']
})
export class EditModalComponent implements OnInit
{
  @Input() userData:User ;

  editFrom: FormGroup = new FormGroup(
    {
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required)
    });

  constructor(public activeModal: NgbActiveModal, private admin: AdminService, private toastrService: ToastrService) {}

  ngOnInit(): void
  {
    this.editFrom.controls["firstName"].setValue(this.userData.firstName);
    this.editFrom.controls["lastName"].setValue(this.userData.lastName);
  }

  public edit(): void
  {
    console.log(this.editFrom.value);
    const data = this.editFrom.value;

    if(this.editFrom.valid)
    {
      this.admin.editUser(data,this.userData.id).subscribe({
        next: (res) =>
        {
          this.toastrService.success("User Edited")
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
