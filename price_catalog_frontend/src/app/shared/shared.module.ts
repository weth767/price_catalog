import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AngularMaterialModule } from '../angular-material.module';
import { PrimeNgModule } from '../prime-ng.module';
import { CustomDialogComponent } from './components/custom-dialog/custom-dialog.component';
import { FooterComponent } from './components/footer/footer.component';
import { NavitemComponent } from './components/navbar/components/navitem/navitem.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { PasswordFieldComponent } from './components/password-field/password-field.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { SearchFieldComponent } from './components/toolbar/components/search-field/search-field.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';

@NgModule({
  declarations: [
    FooterComponent,
    ToolbarComponent,
    SidebarComponent,
    NavbarComponent,
    NavitemComponent,
    SearchFieldComponent,
    ProductCardComponent,
    CustomDialogComponent,
    PasswordFieldComponent,
  ],
  imports: [CommonModule, AngularMaterialModule, PrimeNgModule],
  exports: [
    FooterComponent,
    ToolbarComponent,
    SidebarComponent,
    NavbarComponent,
    ProductCardComponent,
    PasswordFieldComponent,
  ],
})
export class SharedModule {}
