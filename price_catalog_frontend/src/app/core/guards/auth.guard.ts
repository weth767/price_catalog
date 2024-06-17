import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Constants } from '../../shared/util/contants';
import { LocalStorage } from '../../shared/util/local-storage';
import { Authentication } from '../models/auth/authentication';
import { LoginForm } from '../models/auth/login-form';
import { UserService } from '../services/user.service';

export const authGuard: CanActivateFn = (route, state) => {
  let authentication = LocalStorage.getItem(
    Constants.AUTH_KEY,
    true
  ) as Authentication;
  let router = inject(Router);
  if (
    authentication &&
    new Date(authentication.expiresIn).getTime() < Date.now()
  ) {
    return true;
  }
  const credentials = LocalStorage.getItem(
    Constants.CREDENTIALS_KEY,
    true
  ) as LoginForm;
  if (!credentials) {
    router.navigate(['/usuario/login']);
    return false;
  }
  let userService = inject(UserService);
  userService.signIn(credentials).subscribe({
    next: (userResponse) => {
      LocalStorage.setItem(
        Constants.AUTH_KEY,
        {
          token: userResponse.token,
          expiresIn: userResponse.expiresIn,
        },
        true
      );
      LocalStorage.setItem(Constants.USER_KEY, userResponse.user, true);
    },
  });
  return true;
};
