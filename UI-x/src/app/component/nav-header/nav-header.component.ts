import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/service/login.service';

@Component({
  selector: 'app-nav-header',
  templateUrl: './nav-header.component.html',
  styleUrls: ['./nav-header.component.css']
})
export class NavHeaderComponent
{
  @Input() role: string;

  constructor(private auth: LoginService, private rtr: Router){}

  logout()
  {
    this.auth.logOut();
    this.rtr.navigateByUrl("");
  }
}
