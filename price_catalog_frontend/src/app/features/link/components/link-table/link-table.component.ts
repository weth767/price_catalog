import { Component, Input } from '@angular/core';
import { Link } from '../../../../core/models/link';
import { Page } from '../../../../core/models/page';

@Component({
  selector: 'app-link-table',
  templateUrl: './link-table.component.html',
  styleUrl: './link-table.component.scss',
})
export class LinkTableComponent {
  @Input() links?: Page<Link>;
  columns = ['URL', 'Verificado', 'Verificado em', 'Ações'];
}
