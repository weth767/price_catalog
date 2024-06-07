import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-navitem',
  templateUrl: './navitem.component.html',
  styleUrl: './navitem.component.scss',
})
export class NavitemComponent {
  @Input() label = '';
  @Input() path = '';
}
