import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs';
import { CustomDialogConfig } from '../../../../core/models/custom-dialog/custom-dialog-config';
import { CustomDialogService } from '../../../../core/services/custom-dialog/custom-dialog.service';
import { UserService } from '../../../../core/services/user.service';
import { Constants } from '../../../../shared/util/contants';
import { LocalStorage } from '../../../../shared/util/local-storage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  @BlockUI() blockUI!: NgBlockUI;
  loginForm = new FormGroup({
    usernameEmail: new FormControl<string | undefined>(
      undefined,
      Validators.required
    ),
    password: new FormControl<string | undefined>(
      undefined,
      Validators.required
    ),
    rememberPassword: new FormControl(false),
  });

  constructor(
    private customDialogService: CustomDialogService,
    private userService: UserService,
    private router: Router
  ) {}

  public get disabledButtonClass(): string {
    return 'w-full bg-blue-gray-300 text-gray-600 hover:bg-blue-gray-400 font-medium rounded-lg text-sm px-5 py-2.5 text-center';
  }

  public get buttonClass(): string {
    return 'w-full text-white bg-blue-600 hover:bg-blue-700 font-medium rounded-lg text-sm px-5 py-2.5 text-center';
  }

  public login(): void {
    if (this.loginForm.invalid) {
      let config = new CustomDialogConfig();
      config.header = 'Ocorreu um erro';
      config.data.text = 'Campos obrigatórios não preenchidos';
      config.data.showCancelButton = false;
      config.data.confirmButtonText = 'OK';
      this.customDialogService.showDialog(config);
      return;
    }
    this.blockUI.start('Carregando...');
    let email: string | undefined | null = undefined;
    let username: string | undefined | null = undefined;
    if (
      this.loginForm.get('usernameEmail')?.value &&
      String(this.loginForm.get('usernameEmail')!.value).includes('@')
    ) {
      email = this.loginForm.get('usernameEmail')!.value;
    } else {
      username = this.loginForm.get('usernameEmail')!.value;
    }
    let loginForm = {
      username: username,
      password: this.loginForm.get('password')?.value,
      email: email,
    };
    this.userService
      .signIn(loginForm)
      .pipe(finalize(() => this.blockUI.stop()))
      .subscribe({
        next: (userResponse) => {
          if (this.loginForm.get('rememberPassword')?.value) {
            LocalStorage.setItem(Constants.CREDENTIALS_KEY, loginForm, true);
          }
          LocalStorage.setItem(
            Constants.AUTH_KEY,
            {
              token: userResponse.token,
              expiresIn: userResponse.expiresIn,
            },
            true
          );
          LocalStorage.setItem(Constants.USER_KEY, userResponse.user, true);
          this.router.navigate(['/']);
        },
        error: (error: HttpErrorResponse) => {
          let config = new CustomDialogConfig();
          config.header = error.statusText;
          config.data.text = error.message;
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          this.customDialogService.showDialog(config);
        },
      });
  }
}
