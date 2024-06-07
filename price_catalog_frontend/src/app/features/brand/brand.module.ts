import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AngularMaterialModule } from '../../angular-material.module';
import { PrimeNgModule } from '../../prime-ng.module';
import { BrandRoutingModule } from './brand-routing.module';
import { BrandComponent } from './brand.component';

@NgModule({
  declarations: [BrandComponent],
  imports: [
    CommonModule,
    BrandRoutingModule,
    AngularMaterialModule,
    PrimeNgModule,
  ],
})
export class BrandModule {}
