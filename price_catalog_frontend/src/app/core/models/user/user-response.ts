import { User } from './user';

export class UserResponse {
  token!: string;
  expiresIn!: Date;
  user!: User;
}
