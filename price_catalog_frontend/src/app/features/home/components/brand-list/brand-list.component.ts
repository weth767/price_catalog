import { Component, OnInit } from '@angular/core';
import { CountBrand } from '../../../../core/models/count-brand';
import { Page } from '../../../../core/models/page';
import { BrandService } from '../../../../core/services/brand/brand.service';

@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrl: './brand-list.component.scss',
})
export class BrandListComponent implements OnInit {
  brands?: Page<CountBrand>;

  constructor(private brandService: BrandService) {}

  ngOnInit(): void {
    this.brandService.getCountedBrandsPage(0, 10).subscribe({
      next: (brand) => {
        this.brands = brand;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
