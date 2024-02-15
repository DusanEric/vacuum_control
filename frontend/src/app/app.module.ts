import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PasswordPipe} from "./pipes/password.pipe";
import { LoginComponent } from './components/login/login.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { VacuumListComponent } from './components/vacuum-list/vacuum-list.component';
import { AddVacuumComponent } from './components/add-vacuum/add-vacuum.component';

@NgModule({
  declarations: [
    AppComponent,
    PasswordPipe,
    LoginComponent,
    UserListComponent,
    AddUserComponent,
    EditUserComponent,
    VacuumListComponent,
    AddVacuumComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
