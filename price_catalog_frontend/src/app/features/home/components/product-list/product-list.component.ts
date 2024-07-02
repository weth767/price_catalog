import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs';
import { CustomDialogConfig } from '../../../../core/models/custom-dialog/custom-dialog-config';
import { Page } from '../../../../core/models/page';
import { Product } from '../../../../core/models/product';
import { CustomDialogService } from '../../../../core/services/custom-dialog/custom-dialog.service';
import { ProductService } from '../../../../core/services/product/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ProductListComponent implements OnInit {
  public products?: Page<Product>;
  public loading = false;

  constructor(
    private productService: ProductService,
    private customDialogService: CustomDialogService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.productService
      .getProducts(0, 10)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (product) => {
          this.products = product;
        },
        error: (error) => {
          let config = new CustomDialogConfig();
          config.header = 'Ocorreu um erro';
          config.data.text = error?.message;
          config.data.showCancelButton = false;
          config.data.confirmButtonText = 'OK';
          this.customDialogService.showDialog(config);
        },
      });
  }
}
