import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginForm } from '../models/auth/login-form';
import { UserResponse } from '../models/user/user-response';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  public signIn(loginForm: LoginForm): Observable<UserResponse> {
    return this.http.post<UserResponse>(
      `${environment.baseUrl}/user/signin`,
      loginForm
    );
  }
}
