import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandListItemComponent } from './brand-list-item.component';

describe('BrandListItemComponent', () => {
  let component: BrandListItemComponent;
  let fixture: ComponentFixture<BrandListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BrandListItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BrandListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
