import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-brand-list-item',
  templateUrl: './brand-list-item.component.html',
  styleUrl: './brand-list-item.component.scss',
})
export class BrandListItemComponent {
  @Input() brand = '';
}
