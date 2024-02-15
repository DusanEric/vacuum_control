import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {UserListComponent} from "./components/user-list/user-list.component";
import {AddUserComponent} from "./components/add-user/add-user.component";
import {EditUserComponent} from "./components/edit-user/edit-user.component";
import {VacuumListComponent} from "./components/vacuum-list/vacuum-list.component";
import {AddVacuumComponent} from "./components/add-vacuum/add-vacuum.component";

const routes: Routes = [
  {
    path: "",
    component: LoginComponent,
  },
  {
    path: "users",
    component: UserListComponent,
  },
  {
    path: "add-user",
    component: AddUserComponent,
  },
  {
    path: "edit-user/:id",
    component: EditUserComponent,
  },
  {
    path: "vacuums",
    component: VacuumListComponent,
  },
  {
    path: "add-vacuum",
    component: AddVacuumComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
