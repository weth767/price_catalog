import { HttpInterceptorFn } from '@angular/common/http';
import { Constants } from '../../shared/util/contants';
import { LocalStorage } from '../../shared/util/local-storage';
import { Authentication } from '../models/auth/authentication';

export const httpRequestInterceptor: HttpInterceptorFn = (request, next) => {
  const route = request.url;
  //not authenticated routes
  if (route.includes('signup') || route.includes('signin')) {
    return next(request);
  }
  const authencation = LocalStorage.getItem(
    Constants.AUTH_KEY,
    true
  ) as Authentication;
  let requestClone = request.clone({
    setHeaders: { Authorization: `Bearer ${authencation.token}` },
  });
  return next(requestClone);
};
