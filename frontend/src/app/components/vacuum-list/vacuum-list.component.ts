import {Component, OnInit} from '@angular/core';
import {Vacuum} from "../../model";
import {VacuumService} from "../../services/vacuum.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-vacuum-list',
  templateUrl: './vacuum-list.component.html',
  styleUrls: ['./vacuum-list.component.css']
})
export class VacuumListComponent implements OnInit{

  vacuums: Vacuum[] = [];
  selectedStatus: string = 'ON';

  constructor(private vacuumService: VacuumService,private router: Router) { }
  ngOnInit(): void {
    this.vacuumService.getVacuums().subscribe((vacuums) => {
      this.vacuums = vacuums;
    })
  }

  startVacuum(vacuumId: number): void {
    this.vacuumService.startVacuum(vacuumId).subscribe(() => {
      this.router.navigate(['/vacuums']);
      console.log('Vacuum starting successfully');
    },error => {
      console.error('Error starting vacuum:', error);
    });
  }

  stopVacuum(vacuumId: number): void {
    this.vacuumService.stopVacuum(vacuumId).subscribe(() => {
      this.router.navigate(['/vacuums']);
      console.log('Vacuum stoping successfully');
    },error => {
        console.error('Error stoping vacuum:', error);
      });
  }

  dischargeVacuum(vacuumId: number): void {
    this.vacuumService.dischargeVacuum(vacuumId).subscribe(() => {
      this.router.navigate(['/vacuums']);
      console.log('Vacuum discharging successfully');
    },error => {
      console.error('Error discharging vacuum:', error);
    });
  }

  removeVacuum(vacuumId: number): void {
    this.vacuumService.removeVacuum(vacuumId).subscribe(() => {
      this.router.navigate(['/vacuums']);
      console.log('Vacuum removed successfully');
    },error => {
      console.error('Error removing vacuum:', error);
    });
  }

  getVacuumsWithStatus(): void {
    // @ts-ignore
    this.vacuumService.getVacuumsWithStatus(this.selectedStatus).subscribe((vacuums) => {
      // @ts-ignore
      this.vacuums = vacuums;
    })
  }

}
