import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularMaterialModule } from '../../angular-material.module';
import { PrimeNgModule } from '../../prime-ng.module';
import { SharedModule } from '../../shared/shared.module';
import { DomainTableComponent } from './components/domain-table/domain-table.component';
import { DomainsToExploreDialogComponent } from './components/domains-to-explore-dialog/domains-to-explore-dialog.component';
import { DomainRoutingModule } from './domain-routing.module';
import { DomainComponent } from './domain.component';

@NgModule({
  declarations: [
    DomainComponent,
    DomainTableComponent,
    DomainsToExploreDialogComponent,
  ],
  imports: [
    CommonModule,
    DomainRoutingModule,
    SharedModule,
    PrimeNgModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
})
export class DomainModule {}
