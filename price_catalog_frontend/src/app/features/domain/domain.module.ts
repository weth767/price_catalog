import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { PrimeNgModule } from '../../prime-ng.module';
import { SharedModule } from '../../shared/shared.module';
import { DomainTableComponent } from './components/domain-table/domain-table.component';
import { DomainRoutingModule } from './domain-routing.module';
import { DomainComponent } from './domain.component';

@NgModule({
  declarations: [DomainComponent, DomainTableComponent],
  imports: [CommonModule, DomainRoutingModule, SharedModule, PrimeNgModule],
})
export class DomainModule {}
