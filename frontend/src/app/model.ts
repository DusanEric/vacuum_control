export interface User {
  userId: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  permissions: string;
}

export interface Token {
  jwt: string;
  authorities: Authorities[]
}

export interface Authorities {
  authority: string;
}

export interface Vacuum {
  vacuumId: number;
  status: string;
  addedBy: User;
  active: boolean;
  count: number;
  version: number;
}
