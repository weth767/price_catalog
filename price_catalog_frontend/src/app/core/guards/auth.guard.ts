import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Contants } from '../../shared/util/contants';
import { LocalStorage } from '../../shared/util/local-storage';
import { Authentication } from '../models/auth/authentication';
import { CustomDialogConfig } from '../models/custom-dialog/custom-dialog-config';
import { CustomDialogService } from '../services/custom-dialog/custom-dialog.service';

export const authGuard: CanActivateFn = (route, state) => {
  let authentication = LocalStorage.getItem(
    Contants.AUTH_KEY,
    true
  ) as Authentication;
  let router = inject(Router);
  let customDialogService = inject(CustomDialogService);
  if (!authentication || authentication.expiresIn.getTime() < Date.now()) {
    router.navigate(['/usuario/login']);
    let config = new CustomDialogConfig();
    config.header = 'Ocorreu um erro';
    config.data.text = 'É necessário se autenticar para acessar essa área.';
    config.data.showCancelButton = false;
    config.data.confirmButtonText = 'OK';
    customDialogService.showDialog(config);
    return false;
  }
  return true;
};
