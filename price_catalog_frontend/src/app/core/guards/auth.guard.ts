import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Constants } from '../../shared/util/contants';
import { LocalStorage } from '../../shared/util/local-storage';
import { Authentication } from '../models/auth/authentication';

export const authGuard: CanActivateFn = (route, state) => {
  let authentication = LocalStorage.getItem(
    Constants.AUTH_KEY,
    true
  ) as Authentication;
  let router = inject(Router);
  if (
    !authentication ||
    new Date(authentication.expiresIn).getTime() < Date.now()
  ) {
    router.navigate(['/usuario/login']);
    return false;
  }
  return true;
};
