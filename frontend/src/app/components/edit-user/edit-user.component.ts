import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../model";
import {ConfigService} from "../../services/config.service";
import {UserService} from "../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit{

  editForm: FormGroup;

  users: User[] = [];

  user: User = {
    userId: 0,
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    permissions: ''
  }


  constructor(private router: Router,private configService: ConfigService,private route: ActivatedRoute, private userService: UserService, private formBuilder: FormBuilder) {
    this.editForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      permissions: ['', Validators.required],
    })
  }

  ngOnInit(): void {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.userService.findUser(id).subscribe(user => {
      this.user = user;

      this.editForm.setValue({
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        permissions: user.permissions
      });

      return user;
    })
  }
  updateUser() {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get('id'));
    this.userService.findUser(id).subscribe(user => {

        this.user = {
          userId: parseInt(<string>this.route.snapshot.paramMap.get('id')),
          firstName: this.editForm.get('firstName')?.value,
          lastName: this.editForm.get('lastName')?.value,
          email: this.editForm.get('email')?.value,
          password: user.password,
          permissions: this.editForm.get('permissions')?.value
        }

      this.userService.updateUser(this.user).subscribe(user => {
        this.editForm.reset()
        this.user = user;
        this.router.navigate(['/users']);
      })

      });
    // this.userService.updateUser(this.user).subscribe(user => {
    //   this.editForm.reset()
    //   this.user = user;
    //   this.router.navigate(['/users']);
    //   // this.users.push(user)
    // })
  }

  deleteUser() {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get('id'));

    this.userService.deleteUser(id).subscribe(
      () => {
        this.router.navigate(['/users']);
        console.log('User deleted successfully');
      },
      error => {
        console.error('Error deleting user:', error);
      }
    );
  }
}
