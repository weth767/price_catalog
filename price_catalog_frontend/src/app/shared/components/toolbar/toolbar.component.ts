import { Component, EventEmitter, Output } from '@angular/core';
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

  constructor() {
    this.user = LocalStorage.getItem(Constants.USER_KEY, true) as
      | User
      | undefined;
  }
}
