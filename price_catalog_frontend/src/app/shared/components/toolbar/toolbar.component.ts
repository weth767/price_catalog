import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../../core/models/user/user';
import { Constants } from '../../util/contants';
import { LocalStorage } from '../../util/local-storage';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
})
export class ToolbarComponent {
  @Output() openOrCloseSideBar = new EventEmitter();
  user?: User;

  constructor(private router: Router) {
    this.user = LocalStorage.getItem(Constants.USER_KEY, true) as
      | User
      | undefined;
  }

  public logout(): void {
    LocalStorage.removeItem(Constants.AUTH_KEY);
    LocalStorage.removeItem(Constants.USER_KEY);
    LocalStorage.removeItem(Constants.CREDENTIALS_KEY);
    this.user = undefined;
    this.openOrCloseSideBar.emit(false);
    this.router.navigate(['/usuario/login']);
  }
}
