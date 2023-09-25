import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { AdminLayoutComponent } from './component/admin-layout/admin-layout.component';

const routes: Routes =
[
  {path: "login", component:LoginComponent},
  {path: "admin", component:AdminLayoutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
