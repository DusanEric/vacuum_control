import {Component, OnInit} from '@angular/core';
import {VacuumService} from "../../services/vacuum.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-vacuum',
  templateUrl: './add-vacuum.component.html',
  styleUrls: ['./add-vacuum.component.css']
})
export class AddVacuumComponent implements OnInit{
  ngOnInit(): void {
  }

  constructor(private vacuumService: VacuumService,private router: Router) {
  }

  addVacuum() : void{
    this.vacuumService.addVacuum().subscribe( () => {
      this.router.navigate(['/vacuums']);
      console.log('Vacuum added successfully');
    },error => {
      console.error('Error adding vacuum:', error);
    })
  }

}
