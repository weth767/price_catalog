import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AngularMaterialModule } from '../../angular-material.module';
import { AuthRoutingModule } from './auth-routing.module';
import { CreateAccountComponent } from './pages/create-account/create-account.component';
import { LoginComponent } from './pages/login/login.component';

@NgModule({
  declarations: [LoginComponent, CreateAccountComponent],
  imports: [CommonModule, AuthRoutingModule, AngularMaterialModule],
})
export class AuthModule {}
