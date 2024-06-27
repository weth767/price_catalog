import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { PrimeNgModule } from '../../prime-ng.module';
import { SharedModule } from '../../shared/shared.module';
import { LinkRoutingModule } from './link-routing.module';
import { LinkComponent } from './link.component';
import { LinkTableComponent } from './components/link-table/link-table.component';

@NgModule({
  declarations: [LinkComponent, LinkTableComponent],
  imports: [CommonModule, LinkRoutingModule, SharedModule, PrimeNgModule],
})
export class LinkModule {}
