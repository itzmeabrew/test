import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { AdminLayoutComponent } from './component/admin-layout/admin-layout.component';
import { UserLayoutComponent } from './component/user-layout/user-layout.component';
import { authGuard } from './auth/auth.guard';

const routes: Routes =
[
  {path: "", component: LoginComponent},
  {path: "admin", component: AdminLayoutComponent, canActivate: [authGuard]},
  {path: "user", component: UserLayoutComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
