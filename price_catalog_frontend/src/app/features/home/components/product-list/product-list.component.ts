import { Component, OnInit } from '@angular/core';
import { Page } from '../../../../core/models/page';
import { Product } from '../../../../core/models/product';
import { ProductService } from '../../../../core/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ProductListComponent implements OnInit {
  public products?: Page<Product>;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getProducts(0, 10).subscribe({
      next: (product) => {
        this.products = product;
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
