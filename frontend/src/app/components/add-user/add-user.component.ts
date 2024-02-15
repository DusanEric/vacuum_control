import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ConfigService} from "../../services/config.service";
import {UserService} from "../../services/user.service";
import {User} from "../../model";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit{

  userForm: FormGroup;

  users: User[] = []


  constructor(private configService: ConfigService, private userService: UserService, private formBuilder: FormBuilder) {
    this.userForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', [Validators.required]],
      permissions: ['', Validators.required],
    })
  }

  ngOnInit(): void {
  }

  addUser() {
    this.userService.addUser(
      this.userForm.get('firstName')?.value,
      this.userForm.get('lastName')?.value,
      this.userForm.get('email')?.value,
      this.userForm.get('password')?.value,
      this.userForm.get('permissions')?.value
    ).subscribe(user => {
      this.userForm.reset()
      this.users.push(user)
    })
  }

}
