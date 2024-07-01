import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomainsToExploreDialogComponent } from './domains-to-explore-dialog.component';

describe('DomainsToExploreDialogComponent', () => {
  let component: DomainsToExploreDialogComponent;
  let fixture: ComponentFixture<DomainsToExploreDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DomainsToExploreDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DomainsToExploreDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
