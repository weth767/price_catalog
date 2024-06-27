import { Component, Input } from '@angular/core';
import { Domain } from '../../../../core/models/domain';
import { Page } from '../../../../core/models/page';

@Component({
  selector: 'app-domain-table',
  templateUrl: './domain-table.component.html',
  styleUrl: './domain-table.component.scss',
})
export class DomainTableComponent {
  @Input() domains?: Page<Domain>;
  columns = ['Nome', 'URL', 'Verificado', 'Verificado em', 'Ações'];
}
