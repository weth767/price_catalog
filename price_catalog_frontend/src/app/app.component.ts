import { Component } from '@angular/core';
import { initFlowbite } from 'flowbite';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  sidebarVisible = false;

  constructor() {}

  ngOnInit() {
    initFlowbite();
  }

  public openOrCloseSideBar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }
}
