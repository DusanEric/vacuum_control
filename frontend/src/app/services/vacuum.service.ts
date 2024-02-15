import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User, Vacuum} from "../model";

@Injectable({
  providedIn: 'root'
})
export class VacuumService {

  private readonly vacuumsEnv = environment.vacuums


  constructor(private httpClient: HttpClient) { }

  getVacuums(): Observable<Vacuum[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.httpClient.get<Vacuum[]>(`${this.vacuumsEnv}/all`, { headers });
  }

  addVacuum(): Observable<Vacuum> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<Vacuum>(`${this.vacuumsEnv}`, {},{ headers })
  }

  removeVacuum(vacuumId: number): Observable<void> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<void>(`${this.vacuumsEnv}/remove/${vacuumId}`, {},{ headers });
  }

  startVacuum(vacuumId: number): Observable<void> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<void>(`${this.vacuumsEnv}/start/${vacuumId}`, {},{ headers });
  }

  stopVacuum(vacuumId: number): Observable<void> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<void>(`${this.vacuumsEnv}/stop/${vacuumId}`, {},{ headers });
  }

  dischargeVacuum(vacuumId: number): Observable<void> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<void>(`${this.vacuumsEnv}/discharge/${vacuumId}`, {},{ headers });
  }

  getVacuumsWithStatus(status : string): Observable<void> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<void>(`${this.vacuumsEnv}/all/${status}`, { headers });
  }
}
