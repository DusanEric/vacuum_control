import {Component, OnInit} from '@angular/core';
import {ConfigService} from "../../services/config.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;

  constructor(private configService: ConfigService, private userService: UserService, private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', [Validators.required]],
    })
  }

  ngOnInit(): void {
  }

  login() {
    this.userService.login(
      this.loginForm.get('email')?.value,
      this.loginForm.get('password')?.value
    ).subscribe(token => {
      this.loginForm.reset()
      this.configService.setToken(token.jwt);
      const authoritiesString = token.authorities.map(auth => auth.authority).join(',');
      this.configService.setAuthorities(authoritiesString);
    })
  }

}
