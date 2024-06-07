import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AngularMaterialModule } from '../../angular-material.module';
import { PrimeNgModule } from '../../prime-ng.module';
import { SharedModule } from '../../shared/shared.module';
import { ProductInfoComponent } from './pages/product-info/product-info.component';
import { ProductRoutingModule } from './product-routing.module';
import { ProductComponent } from './product.component';

@NgModule({
  declarations: [ProductComponent, ProductInfoComponent],
  imports: [
    CommonModule,
    ProductRoutingModule,
    SharedModule,
    PrimeNgModule,
    AngularMaterialModule,
  ],
})
export class ProductModule {}
