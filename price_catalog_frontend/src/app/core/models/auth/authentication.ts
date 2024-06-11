import { Role } from '../role';

export class Authentication {
  token!: string;
  expiresIn!: Date;
  roles = new Array<Role>();
}
