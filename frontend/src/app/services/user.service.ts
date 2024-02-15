import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Token, User} from "../model";
import {jwtDecode} from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly usersList = environment.usersList
  private readonly loginApi = environment.login

  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<User[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.httpClient.get<User[]>(`${this.usersList}/all`, { headers });
  }

  addUser(firstName: string, lastName: string,email: string, password: string, permissions: string): Observable<User> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<User>(`${this.usersList}`, {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      permissions: permissions,
    },{ headers })
  }

  findUser(id: number): Observable<User> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<User>(`${this.usersList}/${id}`,{ headers })
  }

  // updateUser(firstName: string, lastName: string,email: string, password: string, permissions: string): Observable<User> {
  //   const token = localStorage.getItem('token');
  //   const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  //   return this.httpClient.put<User>(`${this.usersList}`, {
  //     firstName: firstName,
  //     lastName: lastName,
  //     email: email,
  //     password: password,
  //     permissions: permissions,
  //   },{ headers })
  // }

  updateUser(user: User): Observable<User> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<User>(`${this.usersList}`, user,{ headers });
  }

  deleteUser(userId: number): Observable<void> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.delete<void>(`${this.usersList}/${userId}`, { headers });
  }

  login(email: string, password: string): Observable<Token> {
    return this.httpClient.post<Token>(`${this.loginApi}`, {
      email: email,
      password: password
    })
  }

  getUserAuthorities(): string[] {
    console.log('Calling getUserAuthorities');
    const authorities = localStorage.getItem('authorities');

    const authoritiesArray = JSON.parse(authorities || '[]') as string[];

    console.log(authoritiesArray);

    return authoritiesArray;
  }

}
