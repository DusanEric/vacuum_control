import { Component } from '@angular/core';
import {ConfigService} from "../../services/config.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private userService: UserService) {
  }

}
