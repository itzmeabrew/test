import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { AdminLayoutComponent } from './component/admin-layout/admin-layout.component';
import { UserLayoutComponent } from './component/user-layout/user-layout.component';
import { authGuard } from './auth/auth.guard';
import { AddAdminComponent } from './component/add-admin/add-admin.component';
import { NotFoundComponent } from './component/not-found/not-found.component';

const routes: Routes =
[
  {path: "", component: LoginComponent},
  {path: "add-admin", component: AddAdminComponent},
  {path: "admin", component: AdminLayoutComponent, canActivate: [authGuard]},
  {path: "user", component: UserLayoutComponent, canActivate: [authGuard]},
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
