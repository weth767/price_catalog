import { Component, NgZone } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-field',
  templateUrl: './search-field.component.html',
  styleUrl: './search-field.component.scss',
})
export class SearchFieldComponent {
  searchTerm = '';

  constructor(private router: Router, private zone: NgZone) {}

  public search(): void {
    this.router
      .navigateByUrl(`/buscar?descricao=${this.searchTerm}`, {
        skipLocationChange: false,
      })
      .then(() => window.location.reload());
  }

  public searchTermChanged(searchTerm: string): void {
    this.searchTerm = searchTerm;
  }
}
