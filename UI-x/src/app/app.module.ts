import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminLayoutComponent } from './component/admin-layout/admin-layout.component';
import { UserLayoutComponent } from './component/user-layout/user-layout.component';
import { LoginComponent } from './component/login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthInterceptor } from './auth/auth.interceptor';
import { NgbModal, NgbModalModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditModalComponent } from './component/admin-layout/edit-modal/edit-modal.component';
import { NavHeaderComponent } from './component/nav-header/nav-header.component';
import { AddUserModalComponent } from './component/admin-layout/add-user-modal/add-user-modal.component';
import { FileDragDropDirective } from './directive/file-drag-drop.directive';
import { FileNamePipe } from './pipe/file-name.pipe';
import { FileTypePipe } from './pipe/file-type.pipe';
import { AddAdminComponent } from './component/add-admin/add-admin.component';
import { NotFoundComponent } from './component/not-found/not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    UserLayoutComponent,
    LoginComponent,
    EditModalComponent,
    NavHeaderComponent,
    AddUserModalComponent,
    FileDragDropDirective,
    FileNamePipe,
    FileTypePipe,
    AddAdminComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }),
    NgbModalModule,
  ],
  providers:
  [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    FileTypePipe,FileNamePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
