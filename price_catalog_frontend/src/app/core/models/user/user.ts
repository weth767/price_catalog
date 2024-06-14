import { Role } from '../role';

export class User {
  id!: number;
  name!: string;
  username!: string;
  email!: string;
  phone?: string;
  createdAt!: Date;
  updatedAt?: Date;
  roles = new Array<Role>();
}
