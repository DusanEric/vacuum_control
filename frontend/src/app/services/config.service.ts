import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private token: string;
  private _authorities: string;

  constructor() {
    this.token = ''
    this._authorities = ''
  }

  setToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  setAuthorities(authorities: string): void {
    this._authorities = authorities;
    localStorage.setItem('authorities', authorities);
  }

  getToken(): string {
    return this.token;
  }

  get authorities(): string {
    return this._authorities;
  }
}
