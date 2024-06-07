import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { AngularMaterialModule } from '../../angular-material.module';
import { PrimeNgModule } from '../../prime-ng.module';
import { SharedModule } from '../../shared/shared.module';
import { BrandListComponent } from './components/brand-list/brand-list.component';
import { BrandListItemComponent } from './components/brand-list/components/brand-list-item/brand-list-item.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';

@NgModule({
  declarations: [
    HomeComponent,
    BrandListComponent,
    BrandListItemComponent,
    ProductListComponent,
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    AngularMaterialModule,
    PrimeNgModule,
    SharedModule,
  ],
})
export class HomeModule {}
