import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const httpErrorInterceptorInterceptor: HttpInterceptorFn = (
  req,
  next
) => {
  let router = inject(Router);
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status == 401) {
        router.navigate(['/usuario/login']);
      }
      return throwError(() => error);
    })
  );
};
